package com.example.shopsneaker.adapter;

public class AdminAdapter extends android.widget.BaseAdapter {
    private android.content.Context context;
    private int layout;
    private java.util.List<com.example.shopsneaker.model.Admin> adminList;

    public AdminAdapter(android.content.Context context, int layout, java.util.List<com.example.shopsneaker.model.Admin> adminList) {
        this.context = context;
        this.layout = layout;
        this.adminList = adminList;
    }

    @Override
    public int getCount() {
        return adminList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder{
        android.widget.TextView texttenManager;
        android.widget.ImageView imghinhManager;
    }
    @Override
    public android.view.View getView(int i, android.view.View view, android.view.ViewGroup viewGroup) {
        com.example.shopsneaker.adapter.AdminAdapter.ViewHolder viewHolder = null;
        if (view == null){
            viewHolder = new ViewHolder();
            android.view.LayoutInflater inflater = (android.view.LayoutInflater) context.getSystemService(android.content.Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);

            viewHolder.texttenManager = view.findViewById(com.example.shopsneaker.R.id.item_manager_name);
            viewHolder.imghinhManager=view.findViewById(com.example.shopsneaker.R.id.item_manager_img);
            view.setTag(viewHolder);
        }else {
            viewHolder = (com.example.shopsneaker.adapter.AdminAdapter.ViewHolder) view.getTag();
        }
        viewHolder.texttenManager.setText(adminList.get(i).getManagerName());
        com.bumptech.glide.Glide.with(context).load(adminList.get(i).getManagerImages()).into(viewHolder.imghinhManager);
        return view;

    }
}
