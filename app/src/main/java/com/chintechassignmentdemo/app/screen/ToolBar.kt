package com.chintechassignmentdemo.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chintechassignmentdemo.app.R



@Preview
@Composable
fun ToolBar(
) {


    Row (horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.color_EDF4F6))
            .statusBarsPadding()
            .padding(top = 20.dp, bottom = 20.dp, start = 7.dp)

    ){

       Text(text = stringResource(id = R.string.password_manager), fontSize = 20.sp, fontFamily = FontFamily.Serif )


    }


}