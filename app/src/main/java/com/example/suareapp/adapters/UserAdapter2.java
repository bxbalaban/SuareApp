package com.example.suareapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.suareapp.R;
import com.example.suareapp.listener.UsersListener;
import com.example.suareapp.listener.UsersListener2;
import com.example.suareapp.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter2 extends RecyclerView.Adapter<UserAdapter2.UserViewHolder>{

    private List<User> users;
    private UsersListener2 usersListener2;

    private List<User> selectedUsers;


    public UserAdapter2(List<User> users,UsersListener usersListener) {
        this.users = users;
        this.usersListener2= (UsersListener2) usersListener;
        selectedUsers=new ArrayList<>();
    }

    public List<User> getSelectedUsers(){
        return selectedUsers;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.item_location_user,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter2.UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }


    @Override
    public int getItemCount() {
        return users.size() ;
    }

    class UserViewHolder extends RecyclerView.ViewHolder{
        TextView textFirstChar, textUserName , textMobile;
        ImageView imagelocationOn,imageSelected;
        ConstraintLayout locationuserContainer;


        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textFirstChar=itemView.findViewById(R.id.textFirstChar);
            textUserName=itemView.findViewById(R.id.textUserName);
            textMobile=itemView.findViewById(R.id.textMobile);
            imagelocationOn=itemView.findViewById(R.id.imagelocationOn);
            locationuserContainer=itemView.findViewById(R.id.locationuserContainer);
            imageSelected=itemView.findViewById(R.id.imageSelected);
        }

        void setUserData(User user){
            textFirstChar.setText(user.name.substring(0,1));
            textUserName.setText(user.name);
            textMobile.setText(user.mobile);

            imagelocationOn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usersListener2.shareLocation(user);
                }
            });



            locationuserContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imageSelected.getVisibility()!=View.VISIBLE){
                        selectedUsers.add(user);
                        imageSelected.setVisibility(View.VISIBLE);
                        imagelocationOn.setVisibility(View.GONE);
                    }
                }
            });


        }
    }
}