package com.example.listadapterexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainListAdapter extends BaseAdapter {
    private ArrayList<Record> mList = new ArrayList<Record>();

    // 현재 아이템의 수를 리턴
    @Override
    public int getCount() {
        return mList.size();
    }

    // 현재 아이템의 오브젝트를 리턴, Object를 상황에 맞게 변경하거나 리턴받은 오브젝트를 캐스팅해서 사용
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    // 아이템 position의 ID 값 리턴
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 출력 될 아이템 관리
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        final ViewHolder holder;

        //재활용 못하게..
        //convertView = null;

        // 리스트가 길어지면서 현재 화면에 보이지 않는 아이템은 itemView가 null인 상태로 들어 옴
        if ( convertView == null ) {
            // view가 null일 경우 커스텀 레이아웃을 얻어 옴
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_main_item, parent, false);

            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.descView = (TextView) convertView.findViewById(R.id.desc);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Record model = mList.get(position);

        if(holder.imageView != null && model.image != null){
            //holder.imageView.setText(model.image);
            AsyncTaskImage loadingImage = new AsyncTaskImage(holder.imageView, model.image);
            loadingImage.execute();
        }

        if(holder.nameView != null && model.name != null){
            holder.nameView.setText(model.name);
        }

        if(holder.descView != null && model.desc != null){
            holder.descView.setText(model.desc);
        }

        return convertView;
    }

    public void setMainList(ArrayList<Record> list){
        this.mList = list;
    }

    // 외부에서 아이템 추가 요청 시 사용
    public void add(Record model) {
        mList.add(model);
    }

    // 외부에서 아이템 삭제 요청 시 사용
    public void remove(int position) {
        mList.remove(position);
    }

    static class ViewHolder {
        ImageView imageView;
        TextView nameView;
        TextView descView;
    }

}
