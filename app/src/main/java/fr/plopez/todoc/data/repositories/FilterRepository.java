package fr.plopez.todoc.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import fr.plopez.todoc.view.main.PossibleSortMethods;

public class FilterRepository {

    private final MutableLiveData<PossibleSortMethods> requiredSortingMethod = new MutableLiveData<>(PossibleSortMethods.RECENT_FIRST);

    public void setRequiredSortingMethod(PossibleSortMethods sortMethod){
        requiredSortingMethod.setValue(sortMethod);
    }

    public LiveData<PossibleSortMethods> getRequiredSortingMethod(){
        return requiredSortingMethod;
    }
}
