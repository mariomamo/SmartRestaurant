package it.unisa.smartrestaurantapp.item.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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
import it.unisa.smartrestaurantapp.entity.Piatto;
import it.unisa.smartrestaurantapp.item.Adapter.CustomAdapterPiatto;
import it.unisa.smartrestaurantapp.activity.ECDishDetails;
import it.unisa.smartrestaurantapp.server.DbManager;
import it.unisa.smartrestaurantapp.service.PiattoService;
import it.unisa.smartrestaurantapp.server.SmartRestaurantDispatcher;

public class ECDishFragment extends Fragment {
    private ArrayList<Piatto> piatti = new ArrayList<>();
    private CustomAdapterPiatto ca;
    private GridView gvPiatti;

    private PiattoService piattoService = new PiattoService();
    private SmartRestaurantDispatcher receiver;
    private String uniqueID = UUID.randomUUID().toString();

    /**
     * Costruisce un ECDishFragment
     * @param type indica la categoria delle pietanze da mostrare
     */
    public ECDishFragment(String type) {
        receiver = new SmartRestaurantDispatcher("ws://" + DbManager.getIp() + ":" + DbManager.getPorta() + "/jms", "/queue/menuServerToTablet");
        piattoService.requestPiatti(uniqueID, type);
    }

    /**
     * Crea la view ed inserisce all'interno i relativi piatti
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return la view creata
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.tv_view_layout_fragment, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                gvPiatti = v.findViewById(R.id.gv_piatti);

                receiver.subscribe(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        try {
                            String piattiJson = message.getStringProperty("piatti");
                            Log.d("Tv", "[" + getClass().getSimpleName() + "] Ho ricevuto: " + message.getStringProperty("UUID"));

                            Gson gson = new Gson();
                            piatti = gson.fromJson(piattiJson, new TypeToken<List<Piatto>>(){}.getType());
                            Log.d("Ec", "[" + getClass().getSimpleName() + "] Piatti convertiti: " + piatti);

                            gvPiatti.post(new Runnable() {
                                @Override
                                public void run() {
                                    ca  = new CustomAdapterPiatto(getContext(), R.layout.list_item_with_image, piatti);
                                    gvPiatti.setAdapter(ca);

                                    gvPiatti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Piatto p = (Piatto) gvPiatti.getItemAtPosition(position);

                                            Intent i = new Intent(v.getContext(), ECDishDetails.class);
                                            i.putExtra("piatto", p);

                                            startActivityForResult(i, 1);
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

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d("MY-DEBUG", "Activity distrutta");
    }
}
