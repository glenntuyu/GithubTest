package com.astro.test.glenntuyu.userdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.astro.test.glenntuyu.data.model.GithubUserModel
import com.astro.test.glenntuyu.domain.GetUserDetailUseCase
import com.astro.test.glenntuyu.ui.userdetail.UserDetailDataView
import com.astro.test.glenntuyu.ui.userdetail.UserDetailViewModel
import com.astro.test.glenntuyu.utils.jsonToObject
import com.astro.test.glenntuyu.utils.shouldBe
import com.astro.test.glenntuyu.utils.shouldBeInstanceOf
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by glenntuyu on 28/05/2022.
 */
class UserDetailTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userDetailViewModel: UserDetailViewModel
    private val getUserDetailUseCase = mockk<GetUserDetailUseCase>(relaxed = true)

    private val username = "qwertey6"
    private val userDetailCommonResponse = "user_detail.json"
    private val mockkUsers: GithubUserModel = userDetailCommonResponse.jsonToObject()
    private val errorMessage = "Failed to get data"

    val usernameSlot = slot<String>()

    @Before
    fun setUp() {
        userDetailViewModel = createViewModel()
    }

    private fun createViewModel(): UserDetailViewModel {
        return UserDetailViewModel(
            getUserDetailUseCase,
        )
    }

    @Test
    fun `Test Get User Detail Success`() {
        `Given GithubService will return GetGithubUserResponseModel`()
        `When view call startSearch`()
        `Then assert username param`()
        `Then assert user detail`()
    }

    private fun `Given GithubService will return GetGithubUserResponseModel`() {
        every { getUserDetailUseCase.execute(any(), any(), capture(usernameSlot)) }.answers {
            firstArg<(GithubUserModel) -> Unit>().invoke(mockkUsers)
        }
    }

    private fun `When view call startSearch`() {
        userDetailViewModel.getUserDetail(username)
    }

    private fun `Then assert username param`() {
        val capturedUsername = usernameSlot.captured

        capturedUsername shouldBe username
    }

    private fun `Then assert user detail`() {
        val data = userDetailViewModel.onGetUserDetailLiveData.value!!

        data.shouldBeInstanceOf<UserDetailDataView>()

        data.id shouldBe mockkUsers.id
        data.login shouldBe mockkUsers.login
        data.avatarUrl shouldBe mockkUsers.avatarUrl
        data.htmlUrl shouldBe mockkUsers.htmlUrl
        data.following shouldBe mockkUsers.following.toString()
        data.followers shouldBe mockkUsers.followers.toString()
    }

    @Test
    fun `Test Get User Detail Failed`() {
        `Given GithubService will return error`()
        `When view call startSearch`()
        `Then assert error`()
    }

    private fun `Given GithubService will return error`() {
        every { getUserDetailUseCase.execute(any(), any(), any()) }.answers {
            secondArg<(Throwable) -> Unit>().invoke(Throwable(errorMessage))
        }
    }

    private fun `Then assert error`() {
        val data = userDetailViewModel.onThrowMessageLiveData.value!!

        data shouldBe errorMessage
    }
}