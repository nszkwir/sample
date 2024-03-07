package com.spitzer.ui.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.spitzer.model.data.CountryModel
import com.spitzer.ui.components.countryCard.FlagCountryCard
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryListLayout(
    modifier: Modifier = Modifier,
    countries: List<CountryModel>,
    onCountryClicked: (CountryModel) -> Unit,
    refreshCountryList: () -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState()
    if (pullRefreshState.isRefreshing) {
        LaunchedEffect(true) {
            refreshCountryList()
            delay(1500)
            pullRefreshState.endRefresh()
        }
    }
    Box(
        modifier = modifier
            .padding(top = 0.dp, start = 20.dp, end = 20.dp, bottom = 0.dp)
            .nestedScroll(pullRefreshState.nestedScrollConnection)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
            items(countries) { country ->
                FlagCountryCard(
                    country = country,
                    onClick = { onCountryClicked(it) },
                    onLongClick = {}
                )
            }
            item {
                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        PullToRefreshContainer(
            modifier = Modifier.align(Alignment.TopCenter),
            state = pullRefreshState,
        )
    }
}
