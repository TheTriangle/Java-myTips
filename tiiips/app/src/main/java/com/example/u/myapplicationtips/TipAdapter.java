package com.example.u.myapplicationtips;
        import java.util.ArrayList;

        import android.content.ContentValues;
        import android.content.Context;
        import android.content.Intent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.BaseAdapter;
        import android.widget.CheckBox;
        import android.widget.CompoundButton;
        import android.widget.CompoundButton.OnCheckedChangeListener;
        import android.widget.ImageView;
        import android.widget.TextView;
        import android.widget.Toast;


public class TipAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Tip> objects;

    TipAdapter(Context context, ArrayList<Tip> tips) {
        ctx = context;
        objects = tips;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Tip p = getTip(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.tvDescr)).setText(p.name);
        ((TextView) view.findViewById(R.id.tvId)).setText(String.valueOf(p.id));

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        cbBuy.setChecked(p.box);
        return view;
    }

    // товар по позиции
    Tip getTip(int position) {
        return ((Tip) getItem(position));
    }

    // содержимое корзины
    ArrayList<Tip> getBox() {
        ArrayList<Tip> box = new ArrayList<Tip>();
        for (Tip p : objects) {
            // если в корзине
            if (p.box)
                box.add(p);
        }
        return box;
    }

    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // меняем данные товра (в корзине или нет)
            getTip((Integer) buttonView.getTag()).box = isChecked;
        }
    };

    /*AdapterView.OnItemClickListener myCheckClickList = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view,
                                int position, long id) {
            Toast toast;
            toast = Toast.makeText(getApplicationContext(),
                    "Заметка записана!", Toast.LENGTH_SHORT);
            toast.show();
            Tip clickedtip = (Tip)parent.getItemAtPosition(position);
            ContentValues cv = new ContentValues();

            Intent intent = new Intent();
            intent.putExtra("name", clickedtip.name);
            intent.putExtra("tip", clickedtip.tip);
            intent.putExtra("id", clickedtip.id);
            setResult(RESULT_OK, intent);
            finish();
        }
        public void onNothingSelected(AdapterView<?> parent) {
        }
            }
        };//*/
}

