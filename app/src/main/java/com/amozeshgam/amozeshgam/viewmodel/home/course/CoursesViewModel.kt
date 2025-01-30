package com.amozeshgam.amozeshgam.viewmodel.home.course

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllCourses
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseAllCoursesList
import com.amozeshgam.amozeshgam.data.model.local.CourseFilter
import com.amozeshgam.amozeshgam.data.model.local.HomeActivityModel
import com.amozeshgam.amozeshgam.data.repository.HomeActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import javax.inject.Inject

@HiltViewModel
class CoursesViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var repository: HomeActivityRepository

    @Inject
    lateinit var model: HomeActivityModel
    fun getPackagesData(): Deferred<ApiResponseAllCourses?> {
        return viewModelScope.async {
            repository.getCoursesData()
        }
    }

    fun filterPackageWithFilteredButton(
        packageData: List<ApiResponseAllCoursesList>?,
        filteredPackageData: List<ApiResponseAllCoursesList>?,
        indexFilter: Int
    ): List<ApiResponseAllCoursesList>? {
        return when (indexFilter) {
            0 -> {
                packageData
            }

            1 -> {
                filteredPackageData!!.filter {
                    it.status == 1
                }
            }

            2 -> {
                filteredPackageData!!.filter {
                    it.status == 2
                }
            }

            else -> {
                packageData
            }
        }
    }

    fun getFilterItemButton(): Array<CourseFilter> {
        return model.courseFilterItem
    }
}