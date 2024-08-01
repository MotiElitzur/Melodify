package motiapps.melodify.features.loading

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import motiapps.melodify.features.loading.domain.usecases.LoadingUserUseCase
import javax.inject.Inject

@HiltViewModel
class LoadingViewModel @Inject constructor(
private val loadingUserUseCase: LoadingUserUseCase
) : ViewModel() {


}