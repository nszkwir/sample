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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.spitzer.data.model.CountryDataModel
import com.spitzer.ui.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CountryListLayout(
    modifier: Modifier = Modifier,
    countries: List<CountryDataModel>
) {
    Column(
        modifier = modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier,
        ) {
            items(countries) { country ->
                Card(
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
                                    country.flags.svg
                                        ?: R.drawable.baseline_broken_image_24
                                ),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(100.dp)
                                    .clipToBounds(),
                                contentScale = ContentScale.Fit,
                            )
                            Column(
                                modifier = Modifier
                                    .padding(vertical = 10.dp, horizontal = 20.dp)
                                    .weight(7f)
                                    .align(Alignment.CenterVertically),
                                verticalArrangement = Arrangement.Top,
                            ) {
                                Text(
                                    text = country.name,
                                    style = TextStyle.Default.copy(
                                        fontSize = 20.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight(600)
                                    )
                                )
                                Text(
                                    text = country.capital,
                                    style = TextStyle.Default.copy(
                                        fontSize = 18.sp,
                                        color = Color.DarkGray,
                                        fontWeight = FontWeight(400),
                                        fontStyle = FontStyle.Italic,
                                    )
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .width(width = 40.dp)
                                    .fillMaxHeight()
                                    .weight(3f)
                                    .align(Alignment.CenterVertically),
                                verticalArrangement = Arrangement.Center,
                            ) {
                                Row(
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_chevron_right_24),
                                        contentDescription = stringResource(R.string.edit_CD),
                                        colorFilter = ColorFilter.tint(Color.Blue.copy(alpha = 0.7f)),
                                        modifier = Modifier
                                            .size(40.dp)
                                            .padding(8.dp),
                                        alignment = Alignment.Center
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
