package gbstracking.contact;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.gbstracking.R;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class contacts extends Fragment {
    RecycleviewContact adpter;
    List<contactsetter> lstcont;
   RecyclerView rec;

    public contacts() {
    }

View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       v=inflater.inflate(R.layout.contacts, container, false);
       rec=v.findViewById(R.id.recycler_contacts);
        LinearLayoutManager linearLayoutManage=new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager=linearLayoutManage;
        rec.setLayoutManager(layoutManager);

        adpter=new RecycleviewContact(getContext(),getAllcontact());
        rec.setAdapter(adpter);

        return v;
    }
    public  List<contactsetter> getAllcontact(){
        List<contactsetter> lis=new ArrayList<>();

        Cursor curs=getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,
                null,null);
        curs.moveToFirst();
        while(curs.moveToNext()){
            lis.add(new contactsetter(curs.getString(curs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                    )), curs.getString(curs.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))));

        }


     return lis;
    }

}
