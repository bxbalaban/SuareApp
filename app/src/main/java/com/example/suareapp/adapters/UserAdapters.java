package com.example.suareapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.suareapp.R;
import com.example.suareapp.listener.UsersListener;
import com.example.suareapp.models.User;
import java.util.ArrayList;
import java.util.List;
public class UserAdapters extends RecyclerView.Adapter<UserAdapters.UserViewHolder>{

    private List<User> users;
    private UsersListener usersListener;
    private List<User> selectedUsers;


    public UserAdapters(List<User> users,UsersListener usersListener) {
        this.users = users;
        this.usersListener=usersListener;
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
                        R.layout.item_container_user,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setUserData(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size() ;
    }

     class UserViewHolder extends RecyclerView.ViewHolder{
        TextView textFirstChar, textUserName , textMobile;
        ImageView imageAudioMeeting,imageVideoMeeting,imageSelected;
        ConstraintLayout userContainer;
        Button sendLoc;


        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textFirstChar=itemView.findViewById(R.id.textFirstChar);
            textUserName=itemView.findViewById(R.id.textUserName);
            textMobile=itemView.findViewById(R.id.textMobile);
            imageAudioMeeting=itemView.findViewById(R.id.imageAudioMeeting);
            imageVideoMeeting=itemView.findViewById(R.id.imageVideoMeeting);
            userContainer=itemView.findViewById(R.id.userContainer);
            imageSelected=itemView.findViewById(R.id.imageSelected);
            sendLoc=itemView.findViewById(R.id.loc);
        }

        void setUserData(User user){
            textFirstChar.setText(user.name.substring(0,1));
            textUserName.setText(user.name);
            textMobile.setText(user.mobile);


            imageAudioMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usersListener.initiateAudioMeeting(user);
                }
            });

            imageVideoMeeting.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usersListener.initiateVideoMeeting(user);
                }
            });
            sendLoc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    usersListener.initiateLocation(user);
                }
            });

            userContainer.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(imageSelected.getVisibility()!=View.VISIBLE){
                        selectedUsers.add(user);
                        imageSelected.setVisibility(View.VISIBLE);
                        imageVideoMeeting.setVisibility(View.GONE);
                        imageAudioMeeting.setVisibility(View.GONE);
                        usersListener.onMultipleUsersAction(true);
                    }
                    return true;
                }
            });

            userContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(imageSelected.getVisibility()==View.VISIBLE) {
                        selectedUsers.remove(user);
                        imageSelected.setVisibility(View.GONE);
                        imageVideoMeeting.setVisibility(View.VISIBLE);
                        imageAudioMeeting.setVisibility(View.VISIBLE);
                        if (selectedUsers.size() == 0) {
                            usersListener.onMultipleUsersAction(false);
                        }
                    }
                    else {
                        if (selectedUsers.size() > 0) {
                            selectedUsers.add(user);
                            imageSelected.setVisibility(View.VISIBLE);
                            imageVideoMeeting.setVisibility(View.GONE);
                            imageAudioMeeting.setVisibility(View.GONE);

                        }
                    }
                }
            });
        }
    }
}