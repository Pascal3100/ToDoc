package fr.plopez.todoc.data.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import fr.plopez.todoc.view.main.PossibleSortMethods;
/**
 * <p>Filter Repository to filter Tasks multiple ways</p>
 *
 * @author Pascal Lopez
 */

public class FilterRepository {

    private final MutableLiveData<PossibleSortMethods> requiredSortingMethod = new MutableLiveData<>(PossibleSortMethods.RECENT_FIRST);

    /**
     * Sets the user selected filter.
     *
     */
    public void setRequiredSortingMethod(PossibleSortMethods sortMethod){
        requiredSortingMethod.setValue(sortMethod);
    }

    /**
     * returns the user selected filter.
     *
     * @return the user selected filter.
     */
    public LiveData<PossibleSortMethods> getRequiredSortingMethod(){
        return requiredSortingMethod;
    }
}
