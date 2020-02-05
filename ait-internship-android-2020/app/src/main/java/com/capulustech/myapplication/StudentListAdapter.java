package com.capulustech.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentViewHolder>
{
    private final LayoutInflater mInflater;
//    ArrayList<String> students;
    List<Student> students;
    Context context;

    public StudentListAdapter(Context mContext, List<Student> mStudents)
    {
        context = mContext;
        mInflater = LayoutInflater.from(context);
        students = mStudents;
    }


    class StudentViewHolder extends RecyclerView.ViewHolder
    {
        TextView studentNameTV;
        TextView usnTV;
        TextView branchTV;
        TextView sectionTV;
        TextView mobileTV;

        CardView cardView;

        public StudentViewHolder(@NonNull View itemView)
        {
            super(itemView);

            Button deleteButton=itemView.findViewById(R.id.btndlt);

            studentNameTV = itemView.findViewById(R.id.studentNameTV);
            usnTV=itemView.findViewById(R.id.usnTV);
            branchTV=itemView.findViewById(R.id.branchTV);
            sectionTV=itemView.findViewById(R.id.sectionTV);
            mobileTV=itemView.findViewById(R.id.mobileTV);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    @NonNull
    @Override
    public StudentListAdapter.StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                   int viewType)
    {
        View mItemView = mInflater.inflate(R.layout.student_list_item,
                parent, false);
        return new StudentViewHolder(mItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position)
    {
        final Student student = students.get(position);
        holder.studentNameTV.setText(student.name);//Set to text view
        holder.usnTV.setText(student.usn);
        holder.branchTV.setText(student.branch);
        holder.sectionTV.setText(student.section);
        holder.mobileTV.setText(student.mobileNumber);

        holder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                Toast.makeText(context, student.name +"Deleted", Toast.LENGTH_LONG).show();
                Student.deleteStudent(view.getContext(),student);
                ((StudentListActivity)context).recreate();
            }
        });
    }
//    public void showAlertDialog()
//    {
//        AlertDialog.Builder alertBuilders;
//        alertBuilders = new AlertDialog.Builder(this);
//        alertBuilders.setTitle("Logout?");
//        alertBuilders.setMessage("Do You Want To Logout?");
//        alertBuilders.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//        alertBuilders.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                String msgs="Logged Out Successfully";
//                finish();
//            }
//        });
//        alertBuilders.create().show();
//    }

    @Override
    public int getItemCount()
    {
        return students.size();
    }
}