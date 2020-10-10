package it.unisa.smartrestaurantapp.item.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;
import it.unisa.smartrestaurantapp.R;
import it.unisa.smartrestaurantapp.entity.Notifica;

public class CCNotificheAdapter extends ArrayAdapter<Notifica> {
    private int resource;
    private LayoutInflater inflater;
    private ImageView iv_img;
    private TextView tv_nomeTavolo;
    private TextView tv_notifica;

    /**
     * Organizza gli elementi da visualizzare nel frammento delle notifiche
     * @param context contesto dove inserire gli elementi
     * @param resource file di layout
     * @param notifiche notifiche da visualizzare
     */
    public CCNotificheAdapter(@NonNull Context context, int resource, List<Notifica> notifiche) {
        super(context, resource, notifiche);
        this.resource = resource;
        this.inflater = LayoutInflater.from(context);
    }

    /**
     * Crea la view da visualizzare
     * @param position posizione
     * @param convertView view
     * @param parent view padre
     * @return view creata
     */
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(resource, parent, false);
        }

        iv_img = convertView.findViewById(R.id.iv_img);
        tv_nomeTavolo = convertView.findViewById(R.id.tv_nomeTavolo);
        tv_notifica = convertView.findViewById(R.id.tv_notifica);

        Notifica notifica = getItem(position);

        if (!notifica.isLetta()) {
            convertView.setBackgroundColor(convertView.getResources().getColor(R.color.secondaryColor));
        } else {
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }

        int imgResource;

        switch (notifica.getCategoria()) {
            case Notifica.Categoria.PAGAMENTO: imgResource = R.drawable.money;
                break;
            case Notifica.Categoria.AIUTO: imgResource = R.drawable.waiter;
                break;
            default: imgResource = R.drawable.money;
                break;
        }

        iv_img.setImageResource(imgResource);
        tv_nomeTavolo.setText(notifica.getMittente());
        tv_notifica.setText(notifica.getTesto());

        return convertView;
    }
}
