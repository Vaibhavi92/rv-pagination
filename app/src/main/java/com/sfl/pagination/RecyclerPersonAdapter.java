package com.sfl.pagination;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerPersonAdapter extends RecyclerView.Adapter<RecyclerPersonAdapter.PersonVH> {

    private final List<Person> data;

    public RecyclerPersonAdapter(List<Person> data) {
        this.data = data;
    }

    @Override
    public PersonVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_list_item, parent, false);
        return new PersonVH(view);
    }

    @Override
    public void onBindViewHolder(PersonVH holder, final int position) {
        Person person = data.get(position);
        holder.tvFullName.setText(String.format("%s %s, %d", person.getFirstName(), person.getLastName(), person.getAge()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void add(List<Person> items) {
        int previousDataSize = this.data.size();
        this.data.addAll(items);
        notifyItemRangeInserted(previousDataSize, items.size());
    }

    public static class PersonVH extends RecyclerView.ViewHolder {
        TextView tvFullName;

        public PersonVH(View view) {
            super(view);
            this.tvFullName = (TextView) view.findViewById(R.id.tv_full_name);
        }
    }

}