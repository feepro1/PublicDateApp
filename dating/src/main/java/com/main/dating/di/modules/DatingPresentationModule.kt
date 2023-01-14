package com.main.dating.di.modules

import com.main.core.DispatchersList
import com.main.dating.domain.interactor.DatingInteractor
import com.main.dating.presentation.communication.DatingCommunication
import com.main.dating.presentation.communication.DatingMotionToastCommunication
import com.main.dating.presentation.communication.DatingUsersCommunication
import com.main.dating.presentation.viewmodel.DatingViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class DatingPresentationModule {

    @Provides
    fun provideDatingViewModelFactory(
        datingInteractor: DatingInteractor,
        datingCommunication: DatingCommunication,
        dispatchers: DispatchersList
    ): DatingViewModelFactory {
        return DatingViewModelFactory(
            datingInteractor = datingInteractor,
            datingCommunication = datingCommunication,
            dispatchers = dispatchers
        )
    }

    @Provides
    fun provideDatingCommunication(
        datingMotionToastCommunication: DatingMotionToastCommunication,
        datingUsersCommunication: DatingUsersCommunication
    ): DatingCommunication {
        return DatingCommunication.Base(
            datingMotionToastCommunication = datingMotionToastCommunication,
            datingUsersCommunication = datingUsersCommunication
        )
    }

    @Provides
    fun provideDatingMotionToastCommunication(): DatingMotionToastCommunication {
        return DatingMotionToastCommunication.Base()
    }

    @Provides
    fun provideDatingUsersCommunication(): DatingUsersCommunication {
        return DatingUsersCommunication.Base()
    }

    @Provides
    fun provideDispatchers(): DispatchersList {
        return DispatchersList.Base()
    }
}