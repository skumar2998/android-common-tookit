package com.lurencun.android.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author : 桥下一粒砂
 * @email  : chenyoca@gmail.com
 * @date   : 2012-9-13
 * @desc   : TODO
 * @param <T>
 */
public class CommonAdapter<T> extends AbstractAdapter<T> {

	public CommonAdapter(LayoutInflater inflater, ViewCreator<T> creator) {
		super(inflater, creator);
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		if(null == convertView){
			return mCreator.createView(mInflater, pos, mDataCache.get(pos));
		}else{
			//mCreator.updateView(convertView, pos, mDataCache.get(pos));
			return convertView;
		}
		
	}

}
