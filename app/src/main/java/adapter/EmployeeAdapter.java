package adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sqlite.R;
import model.Employee;

import java.util.List;

public class EmployeeAdapter extends BaseAdapter {

    private final Context context;
    private List<Employee> list;

    // Constructor
    public EmployeeAdapter(Context context, List<Employee> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list != null ? list.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Nếu convertView chưa tồn tại thì inflate layout và tạo ViewHolder
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_employee_item, parent, false);
            holder = new ViewHolder();
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvSalary = convertView.findViewById(R.id.tvSalary);
            convertView.setTag(holder); // Gán ViewHolder vào view để tái sử dụng
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Employee emp = list.get(position);
        holder.tvName.setText(emp.getName());
        holder.tvSalary.setText(String.valueOf(emp.getSalary()));

        return convertView;
    }

    public void setList(List<Employee> list) {
        this.list = list;
        notifyDataSetChanged(); // Cập nhật lại list và refresh UI
    }

    static class ViewHolder {
        TextView tvName;
        TextView tvSalary;
    }
}
