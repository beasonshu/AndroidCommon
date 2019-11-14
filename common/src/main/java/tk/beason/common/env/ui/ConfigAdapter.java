package tk.beason.common.env.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import tk.beason.common.R;
import tk.beason.common.env.model.Variable;


public class ConfigAdapter extends BaseAdapter {

    private final List<Variable> variables;

    ConfigAdapter(List<Variable> variables) {
        this.variables = variables;
    }

    @Override
    public int getCount() {
        return variables.size();
    }

    @Override
    public Variable getItem(int position) {
        return variables.get(position);
    }

    @Override
    public long getItemId(int position) {
        return variables.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.env_var_item_variable, null);
            holder = new Holder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        final Variable variable = getItem(position);
        holder.tvDesc.setText(variable.desc);
        holder.tvValue.setText(variable.getValue());

        return convertView;
    }

    static class Holder {

        final View rootView;
        TextView tvDesc;
        TextView tvValue;

        Holder(View rootView) {
            this.rootView = rootView;
            tvDesc = rootView.findViewById(R.id.tv_desc);
            tvValue = rootView.findViewById(R.id.tv_value);
        }
    }
}

