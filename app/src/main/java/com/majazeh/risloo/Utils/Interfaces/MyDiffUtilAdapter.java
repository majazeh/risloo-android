package com.majazeh.risloo.Utils.Interfaces;

import com.mre.ligheh.Model.TypeModel.TypeModel;

public interface MyDiffUtilAdapter {

    boolean areItemsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel);

    boolean areContentsTheSame(TypeModel oldTypeModel, TypeModel newTypeModel);

}