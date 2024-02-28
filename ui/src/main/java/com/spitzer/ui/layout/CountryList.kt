package com.spitzer.ui.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.spitzer.model.data.CountryModel
import com.spitzer.ui.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun CountryListLayout(
    modifier: Modifier = Modifier,
    countries: List<CountryModel>,
    onCountryClicked: (CountryModel) -> Unit,
    refreshCountryList: () -> Unit
) {
    val pullRefreshState = rememberPullToRefreshState(

    )
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

                OutlinedCard(
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .fillMaxWidth()
                        .height(100.dp)

                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .clipToBounds()
                                .combinedClickable(
                                    onClick = {
                                        //onCountryClicked(country)
                                    },
                                    onLongClick = {
                                        //countrySelected(country, true)
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(
                                    country.flag?.svg ?: R.drawable.baseline_broken_image_24
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(6f)
                                    .clipToBounds(),
                                contentScale = ContentScale.FillBounds,
                            )
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 10.dp, horizontal = 20.dp)
                                    .weight(7f)
                                    .align(Alignment.CenterVertically),
                                verticalArrangement = Arrangement.Top,
                            ) {
                                Text(
                                    text = country.name.common,
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = country.capital,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .width(width = 40.dp)
                                    .fillMaxHeight()
                                    .weight(2f)
                                    .align(Alignment.CenterVertically),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Row(
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                                        contentDescription = stringResource(R.string.edit_CD),
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(8.dp)
                                            .align(Alignment.CenterVertically),
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                }
                            }
                        }
                    }
                }
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
