package com.example.shopsneaker.adapter;

public class AccountAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<AccountAdapter.MyViewHolder> implements android.widget.Filterable{
    android.content.Context context;
    java.util.List<com.example.shopsneaker.model.User> array;
    java.util.List<com.example.shopsneaker.model.User> arrayOld;

    public AccountAdapter(android.content.Context context, java.util.List<com.example.shopsneaker.model.User> array) {
        this.context = context;
        this.array = array;
        arrayOld=array;
    }

    @androidx.annotation.NonNull
    @Override
    public com.example.shopsneaker.adapter.AccountAdapter.MyViewHolder onCreateViewHolder(@androidx.annotation.NonNull android.view.ViewGroup parent, int viewType) {
        android.view.View item = android.view.LayoutInflater.from(parent.getContext()).inflate(com.example.shopsneaker.R.layout.item_account, parent, false);
        return new AccountAdapter.MyViewHolder(item);
    }
    private String GetTenQuyen(int roleid){
        String result ="";
        switch (roleid){
            case 1:
                result = "Admin";
                break;
            case 2:
                result="Staff";
                break;
            case 3:
                result="Customer";
                break;
        }
        return result;
    }

    @Override
    public void onBindViewHolder(@androidx.annotation.NonNull com.example.shopsneaker.adapter.AccountAdapter.MyViewHolder holder, int position) {
        com.example.shopsneaker.model.User user = array.get(position);
        holder.txtID.setText("ID: "+user.getAccountid().toString());
        holder.txtUserName.setText("Tên đăng nhập: "+user.getUsername());
        String s= "Quyền: ";
        holder.txtRoleid.setText(s+GetTenQuyen(user.getRolesid()));

        holder.txtName.setText("Tên: "+user.getName());
        holder.txtAddres.setText("Địa chỉ: "+user.getAddress());
        holder.txtPhone.setText("Điện thoại: "+user.getPhone());
        if (user.getEnabled()==1){
            holder.txtTrangThai.setText("Trạng thái: Hoạt động");
            holder.txtTrangThai.setTextColor(android.graphics.Color.rgb(0,255,0));
        }else {
            holder.txtTrangThai.setText("Trạng thái: Đang bị khóa");
            holder.txtTrangThai.setTextColor(android.graphics.Color.rgb(255,0,0));
        }
        holder.setListener((view, position1, isLongClick) -> {
            if (!isLongClick){

            }else {

                org.greenrobot.eventbus.EventBus.getDefault().postSticky(new com.example.shopsneaker.model.EventBus.AccountEvent(user));
            }
        });


    }


    @Override
    public int getItemCount() {
        return array.size();
    }

    @Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {
            @Override
            protected android.widget.Filter.FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()){
                    array=arrayOld;
                }
                else {
                    java.util.List<com.example.shopsneaker.model.User> userList = new java.util.ArrayList<>();
                    for (com.example.shopsneaker.model.User userr: arrayOld){
                        if (userr.getName()!= null){
                            if (userr.getName().toLowerCase().contains(strSearch.toLowerCase())){
                                userList.add(userr);
                            }
                        }
                    }
                    array=userList;
                }
                android.widget.Filter.FilterResults filterResults = new android.widget.Filter.FilterResults();
                filterResults.values =array;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, android.widget.Filter.FilterResults filterResults) {

                array= (java.util.List<com.example.shopsneaker.model.User>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder implements android.view.View.OnLongClickListener {

        public android.widget.TextView txtID, txtTrangThai, txtUserName, txtRoleid,txtName, txtAddres, txtPhone;
        com.example.shopsneaker.interFace.ItemLongClickListener listener;


        public MyViewHolder(@androidx.annotation.NonNull android.view.View itemView) {
            super(itemView);
            txtID = itemView.findViewById(com.example.shopsneaker.R.id.IDAccount);
            txtTrangThai = itemView.findViewById(com.example.shopsneaker.R.id.TrangThai);
            txtUserName = itemView.findViewById(com.example.shopsneaker.R.id.Username);
            txtRoleid = itemView.findViewById(com.example.shopsneaker.R.id.Roleid);
            txtName= itemView.findViewById(com.example.shopsneaker.R.id.Name);
            txtAddres = itemView.findViewById(com.example.shopsneaker.R.id.Address);
            txtPhone= itemView.findViewById(com.example.shopsneaker.R.id.Phone);
            itemView.setOnLongClickListener(this);

        }

        public void setListener(com.example.shopsneaker.interFace.ItemLongClickListener listener) {
            this.listener = listener;
        }

        @Override
        public boolean onLongClick(android.view.View view) {
            listener.onClick(view,getAdapterPosition(),true);
            return false;
        }
    }
}
