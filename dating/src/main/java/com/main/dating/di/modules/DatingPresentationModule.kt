package com.main.dating.di.modules

import com.main.core.DispatchersList
import com.main.dating.domain.ManageDirection
import com.main.dating.domain.ProvideAnimationsSettings
import com.main.dating.domain.interactor.DatingInteractor
import com.main.dating.presentation.communication.DatingCommunication
import com.main.dating.presentation.communication.DatingMotionToastCommunication
import com.main.dating.presentation.communication.DatingUserCommunication
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
        dispatchers: DispatchersList,
        manageDirection: ManageDirection,
        provideAnimationsSettings: ProvideAnimationsSettings
    ): DatingViewModelFactory {
        return DatingViewModelFactory(
            datingInteractor = datingInteractor,
            datingCommunication = datingCommunication,
            dispatchers = dispatchers,
            manageDirection = manageDirection,
            provideAnimationsSettings = provideAnimationsSettings
        )
    }

    @Provides
    fun provideDatingCommunication(
        datingMotionToastCommunication: DatingMotionToastCommunication,
        datingUsersCommunication: DatingUsersCommunication,
        datingUserCommunication: DatingUserCommunication
    ): DatingCommunication {
        return DatingCommunication.Base(
            datingMotionToastCommunication = datingMotionToastCommunication,
            datingUsersCommunication = datingUsersCommunication,
            datingUserCommunication = datingUserCommunication
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
    fun provideDatingUserCommunication(): DatingUserCommunication {
        return DatingUserCommunication.Base()
    }

    @Provides
    fun provideDispatchers(): DispatchersList {
        return DispatchersList.Base()
    }
}