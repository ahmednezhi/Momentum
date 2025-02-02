package com.ahmed_nezhi.home_presentation.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed_nezhi.home_presentation.R

@Composable
 fun WelcomeTitle() {
    Text(
        text = buildAnnotatedString {
            append("Hey, ")

            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                append(stringResource(R.string.welcome))
            }
        },
        modifier = Modifier.padding(bottom = 16.dp, start = 8.dp),
        fontSize = 22.sp
    )
}