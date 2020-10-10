package it.unisa.smartrestaurantapp.item.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Notifica;
import it.unisa.smartrestaurantapp.item.Adapter.CCNotificheAdapter;
import it.unisa.smartrestaurantapp.service.NotificaService;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;

public class CCTavoloNotificheFragment extends Fragment {
    private ListView lv_notifiche;
    private ArrayList<Notifica> notifiche;
    private CCNotificheAdapter adapter;

    private SmartRestaurantDispatcher receiver;
    private String uniqueID = UUID.randomUUID().toString();
    private NotificaService notificaService = new NotificaService();

    /**
     * Costruisce il frammento per mostrare le notifiche
     */
    public CCTavoloNotificheFragment() {
        receiver = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/topic/notificaServerToTablet");
        notificaService.requestNotifiche(uniqueID, "0", "100", "ccameriere");
    }

    /**
     * Crea la view ed inserisce all'interno le notifiche
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la view creata
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.cc_notifiche_view, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                lv_notifiche = view.findViewById(R.id.lv_notifiche);

                receiver.subscribe(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            String notificheJson = message.getStringProperty("notifica");
                            Log.d("CCameriere", "[" + getClass().getSimpleName() + "] Ho ricevuto: " + message.getStringProperty("UUID"));

                            Gson gson = new Gson();
                            notifiche = gson.fromJson(notificheJson, new TypeToken<List<Notifica>>(){}.getType());

                            lv_notifiche.post(new Runnable() {
                                  public void run() {
                                      adapter = new CCNotificheAdapter(view.getContext(), R.layout.cc_notification_popup, notifiche);

                                      if (lv_notifiche.getAdapter() == null)
                                          lv_notifiche.setAdapter(adapter);

                                      lv_notifiche.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                          @Override
                                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                              Notifica notifica = notifiche.get(position);
                                              if (!notifica.isLetta()) {
                                                  notifica.setLetta(true);
                                                  view.setBackgroundColor(Color.TRANSPARENT);
                                                  notificaService.sendNotifica(uniqueID, notifica);
                                              }
                                          }
                                      });
                                  }
                              });
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }, "UUID = '" + uniqueID + "'");
            }
        }).start();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        receiver.unsubscribe();
        receiver.disconnessione();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        receiver.unsubscribe();
        receiver.disconnessione();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        receiver.unsubscribe();
        receiver.disconnessione();
    }

    @Override
    public void onStop() {
        super.onStop();
        receiver.unsubscribe();
        receiver.disconnessione();
    }
}
