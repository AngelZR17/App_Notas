package com.example.notes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notes.ui.theme.NotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(){
    Box(modifier = Modifier
        .height(80.dp)
        .fillMaxSize()
    ){
        TopAppBar(
            //modifier = Modifier.padding(top = 80.dp),
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
            title = {
                Text(stringResource(id = R.string.title), color = Color.White, fontWeight = FontWeight.Bold)
            },
            actions = {
                TopBarActions()
            }
        )
    }
}

@Composable
fun TopBarActions(){
    MenuAction()
}
@Composable
fun MenuAction(){
    val context = LocalContext.current
    IconButton(onClick = {}
    ){
        Icon(imageVector = Icons.Filled.Menu, contentDescription = "menu_icon", tint = Color.White)
    }
}

@Preview
@Composable
fun TopBarPreview(){
    NotesTheme {
        TopBar()
    }
}