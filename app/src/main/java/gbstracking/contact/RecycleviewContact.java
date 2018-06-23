package gbstracking.contact;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gbstracking.R;

import java.util.List;

/**
 * Created by HP on 23/05/2018.
 */

public class RecycleviewContact extends RecyclerView.Adapter<RecycleviewContact.Myviewholder> {
     Context context;
     List<contactsetter> con;

    public RecycleviewContact(Context context, List<contactsetter> con) {
        this.context = context;
        this.con = con;
    }

    @Override
    public Myviewholder onCreateViewHolder(ViewGroup parent, int viewType) {
  View v;
  v= LayoutInflater.from(context).inflate(R.layout.textcontact,parent,false);
   Myviewholder my=new Myviewholder(v);

  return my;
    }

    @Override
    public void onBindViewHolder(Myviewholder holder, final int position) {
        holder.contactname.setText(con.get(position).getName());
        holder.contactphone.setText(con.get(position).getPhone());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + con.get(position).getPhone()));
                intent.putExtra("sms_body", "https://play.google.com/store/apps/details?id=zamaleekonlinee.zamalekonline");
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return con.size();
    }

    public static class Myviewholder extends RecyclerView.ViewHolder
    {
        private TextView contactname;
        private TextView contactphone;
        public Myviewholder(View itemView) {
            super(itemView);
            contactname=itemView.findViewById(R.id.textnamecont);
            contactphone=itemView.findViewById(R.id.numbercontact);



        }


    }
}
