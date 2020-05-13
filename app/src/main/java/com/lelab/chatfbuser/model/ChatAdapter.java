package com.lelab.chatfbuser.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lelab.chatfbuser.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static int TYPE_ME = 1;
    private static int TYPE_FRIEND = 2;
    private Context context;
    private List<Chat> chats;
    private String me;

    public ChatAdapter(Context context, List<Chat> chats, String me) {
        this.context = context;
        this.chats = chats;
        this.me = me;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if (viewType == TYPE_ME) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_chat_me, viewGroup, false);
            return new MeViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_chat_friend, viewGroup, false);
            return new FriendViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        if (getItemViewType(position) == TYPE_ME) {
            ((MeViewHolder) viewHolder).setMeDetails(chats.get(position));
        } else {
            ((FriendViewHolder) viewHolder).setFriendDetails(chats.get(position));
        }
    }

    public void addChats(List<Chat> chats) {
        this.chats = chats;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chats.get(position).getSender().equals(me)) {
            return TYPE_ME;
        } else {
            return TYPE_FRIEND;
        }
    }

    static class MeViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMessage;

        MeViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.txtName);
            tvMessage = view.findViewById(R.id.txtMessage);
        }

        private void setMeDetails(Chat chat) {
            tvName.setText(chat.getSender());
            tvMessage.setText(chat.getMessage());
        }
    }

    static class FriendViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvMessage;

        FriendViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.txtName);
            tvMessage = view.findViewById(R.id.txtMessage);
        }

        private void setFriendDetails(Chat chat) {
            tvName.setText(chat.getSender());
            tvMessage.setText(chat.getMessage());
        }
    }
}
