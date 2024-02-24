package com.spitzer.ui.feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.spitzer.ui.R
import com.spitzer.ui.layout.scaffold.ScaffoldLayout
import com.spitzer.ui.layout.scaffold.TopBarConfiguration
import com.spitzer.ui.layout.scaffold.TopBarLayout

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onBackClicked: () -> Unit
) {
    ScaffoldLayout(
        topBarContent = {
            TopBarLayout(
                configuration = TopBarConfiguration(
                    title = stringResource(id = R.string.settings),
                    navIconId = R.drawable.baseline_arrow_back_24,
                    onNavIconClicked = onBackClicked
                )
            )
        },
    ) {
        Column(
            modifier = Modifier
                .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedButton(
                onClick = viewModel::updateCountries
            ) {
                Text(text = stringResource(id = R.string.restoreCountriesData))
                Image(
                    painter = painterResource(id = R.drawable.baseline_restore_24),
                    contentDescription = stringResource(id = R.string.restoreCountriesData_CD)
                )
            }
        }
    }
}
