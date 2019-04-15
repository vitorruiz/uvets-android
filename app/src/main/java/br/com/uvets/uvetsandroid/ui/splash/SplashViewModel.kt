package br.com.uvets.uvetsandroid.ui.splash

import br.com.uvets.uvetsandroid.data.repository.UserRepository
import br.com.uvets.uvetsandroid.ui.base.BaseNavigator
import br.com.uvets.uvetsandroid.ui.base.BaseViewModel

class SplashViewModel(userRepository: UserRepository) : BaseViewModel<BaseNavigator>(userRepository)