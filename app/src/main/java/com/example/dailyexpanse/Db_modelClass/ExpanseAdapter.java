package com.example.dailyexpanse.Db_modelClass;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailyexpanse.Fragment_classes.Show_expanse_frag;
import com.example.dailyexpanse.MainActivity;
import com.example.dailyexpanse.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ExpanseAdapter  extends RecyclerView.Adapter<ExpanseAdapter.ExpanseViewholder> {

    Context context;
   List<ExpanseModel>expanseModel;

    private TextView expenseType,expenseAmount,expenseDate,expenseTime;
    private Button showDocumentBtn;

    public ExpanseAdapter(List<ExpanseModel> expenseList, FragmentActivity activity) {
        this.expanseModel=expenseList;
        context=activity;
    }

    @NonNull
    @Override
    public ExpanseViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.expanse_daily_recyclerview,null);
        return new ExpanseViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ExpanseViewholder holder, final int position) {

        final ExpanseModel expanseModel1=expanseModel.get(position);
        holder.expenseAmountTV.setText(expanseModel1.getExpense_amount());
        holder.expenseDateTV.setText(expanseModel1.getExpense_date());
        holder.expenseTypeTV.setText(expanseModel1.getExpense_time());

        //recycler view item click event to show details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View view = LayoutInflater.from(context).inflate(R.layout.fragment_bottomsheetfrag, null);
                expenseType = view.findViewById(R.id.expenseTypeTVId);
                expenseAmount = view.findViewById(R.id.expenseAmountTVId);
                expenseDate = view.findViewById(R.id.expenseDateTVId);
                expenseTime = view.findViewById(R.id.expenseTimeTVId);
                showDocumentBtn = view.findViewById(R.id.showDocumentBtnId);

                expenseType.setText(expanseModel1.getExpense_item());
                expenseAmount.setText(expanseModel1.getExpense_amount()+" BDT");
                expenseDate.setText(expanseModel1.getExpense_date());

                //time empty checking
                if(expanseModel1.getExpense_time() == null || expanseModel1.getExpense_time().isEmpty()){
                    expenseTime.setText("No time Added");
                }else {
                    expenseTime.setText(expanseModel1.getExpense_time());
                }

                showDocumentBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Dialog builder = new Dialog(context);
                        View  view = LayoutInflater.from(context).inflate(R.layout.image_view_layout_design,null);
                        builder.setTitle("Document of "+expanseModel1.getExpense_item());
                        builder.setContentView(view);

                        ImageView imageView = view.findViewById(R.id.imageViewLayoutDesignId);
                        imageView.setImageURI(Uri.parse(expanseModel1.getExpense_image()));
                        builder.show();
                    }
                });

                BottomSheetDialog dialog = new BottomSheetDialog(context);
                dialog.setContentView(view);
                dialog.show();
            }
        });

        //recycler view more button click action
        holder.moreIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(context,holder.moreIV);
                popupMenu.inflate(R.menu.edit_menu_item);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()){

                            case R.id.updateOptionId:
                                //update option click action
                                Intent intent = new Intent(context, MainActivity.class);

                                intent.putExtra("EXPENSE_ID",expanseModel1.getId());
                                intent.putExtra("result",100);
                                context.startActivity(intent);

                                return true;

                            case R.id.deleteOptionId:
                                //delete option click action
                                Cursor cursor = Show_expanse_frag.myDBHelper.getData("SELECT id FROM hisabnikas");
                                List<Integer> id = new ArrayList<>();

                                while (cursor.moveToNext()){
                                    id.add(cursor.getInt(0));
                                }

                                showDeleteDialog(id.get(position),position);

                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
    private void showDeleteDialog(final int rowId, final int position) {

        AlertDialog.Builder deleteDialog = new AlertDialog.Builder(context);
        deleteDialog.setTitle("Warning!");
        deleteDialog.setMessage("Are you sure to delete?");

        deleteDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Show_expanse_frag.myDBHelper.deleteDataFromDatabase(rowId);
                    Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                    expanseModel.remove(position);
                    notifyDataSetChanged();
                }catch (Exception e){
                    Toast.makeText(context, "Exception: "+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        deleteDialog.show();
    }


    @Override
    public int getItemCount() {
        return expanseModel.size();
    }

    public class ExpanseViewholder extends RecyclerView.ViewHolder {
        private TextView expenseTypeTV,expenseDateTV,expenseAmountTV;
        private ImageView moreIV;
        public ExpanseViewholder(@NonNull View itemView) {
            super(itemView);
            expenseTypeTV = itemView.findViewById(R.id.expenseTypeTVId);
            expenseDateTV = itemView.findViewById(R.id.expenseDateTVId);
            expenseAmountTV = itemView.findViewById(R.id.expenseAmountTVId);
            moreIV = itemView.findViewById(R.id.moreIVId);
        }
    }
}
