package com.amazingTLR.opensample.common.composable

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

// A TextField that will display the text only if there is a value for it
// Prevent empty spaces from being displayed in UI
@Composable
fun DynamicTextField(
    defaultValue: String?,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
) {
    val value by remember { mutableStateOf(defaultValue) }

    value?.let {
        Text(text = it, style = style, modifier = modifier)
    }
}