/*
 * This file is part of Neo Launcher
 * Copyright (c) 2022   Neo Launcher Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.saggitt.omega.compose.components.preferences

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.launcher3.Utilities
import com.saggitt.omega.compose.components.DialogNegativeButton
import com.saggitt.omega.compose.components.DialogPositiveButton
import com.saggitt.omega.iconIds
import com.saggitt.omega.preferences.BasePreferences

@Composable
fun IntSelectionPrefDialogUI(
    pref: BasePreferences.IntSelectionPref,
    openDialogCustom: MutableState<Boolean>
) {
    val context = LocalContext.current
    val prefs = Utilities.getOmegaPrefs(context)
    var selected by remember { mutableStateOf(pref.onGetValue()) }
    val entryPairs = pref.entries.toList()

    var radius = 16.dp
    if (prefs.themeCornerRadiusOverride.onGetValue()) {
        radius = prefs.themeCornerRadius.onGetValue().dp
    }
    val cornerRadius by remember { mutableStateOf(radius) }

    Card(
        shape = RoundedCornerShape(cornerRadius),
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = stringResource(pref.titleId), style = MaterialTheme.typography.titleLarge)
            LazyColumn(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .weight(1f, false)
            ) {
                items(items = entryPairs) {
                    val isSelected = rememberSaveable(selected) {
                        mutableStateOf(selected == it.first)
                    }
                    Row( // TODO abstract to SingleSelectionListItem
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selected = it.first
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected.value,
                            onClick = {
                                selected = it.first
                            },
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = stringResource(id = it.second),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Row(
                Modifier.fillMaxWidth()
            ) {
                DialogNegativeButton(
                    cornerRadius = cornerRadius,
                    onClick = { openDialogCustom.value = false }
                )
                Spacer(Modifier.weight(1f))
                DialogPositiveButton(
                    modifier = Modifier.padding(start = 16.dp),
                    cornerRadius = cornerRadius,
                    onClick = {
                        pref.onSetValue(selected)
                        openDialogCustom.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun StringSelectionPrefDialogUI(
    pref: BasePreferences.StringSelectionPref,
    openDialogCustom: MutableState<Boolean>
) {
    val context = LocalContext.current
    val prefs = Utilities.getOmegaPrefs(context)
    var selected by remember { mutableStateOf(pref.onGetValue()) }
    val entryPairs = pref.entries.toList()

    var radius = 16.dp
    if (prefs.themeCornerRadiusOverride.onGetValue()) {
        radius = prefs.themeCornerRadius.onGetValue().dp
    }
    val cornerRadius by remember { mutableStateOf(radius) }

    Card(
        shape = RoundedCornerShape(cornerRadius),
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = stringResource(pref.titleId), style = MaterialTheme.typography.titleLarge)
            LazyColumn(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .weight(1f, false)
            ) {
                items(items = entryPairs) {
                    val isSelected = rememberSaveable(selected) {
                        mutableStateOf(selected == it.first)
                    }
                    Row( // TODO abstract to SingleSelectionListItem
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selected = it.first
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected.value,
                            onClick = {
                                selected = it.first
                            },
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            colors = RadioButtonDefaults.colors(
                                selectedColor = MaterialTheme.colorScheme.primary,
                                unselectedColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        Text(
                            text = it.second,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Row(
                Modifier.fillMaxWidth()
            ) {
                DialogNegativeButton(
                    cornerRadius = cornerRadius,
                    onClick = { openDialogCustom.value = false }
                )
                Spacer(Modifier.weight(1f))
                DialogPositiveButton(
                    modifier = Modifier.padding(start = 16.dp),
                    cornerRadius = cornerRadius,
                    onClick = {
                        pref.onSetValue(selected)
                        openDialogCustom.value = false
                    }
                )
            }
        }
    }
}

@Composable
fun StringMultiSelectionPrefDialogUI(
    pref: BasePreferences.StringMultiSelectionPref,
    openDialogCustom: MutableState<Boolean>
) {
    val context = LocalContext.current
    val prefs = Utilities.getOmegaPrefs(context)
    var selected by remember { mutableStateOf(pref.onGetValue()) }
    val entryPairs = pref.entries.toList()

    var radius = 16.dp
    if (prefs.themeCornerRadiusOverride.onGetValue()) {
        radius = prefs.themeCornerRadius.onGetValue().dp
    }
    val cornerRadius by remember { mutableStateOf(radius) }

    Card(
        shape = RoundedCornerShape(cornerRadius),
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = stringResource(pref.titleId), style = MaterialTheme.typography.titleLarge)
            LazyColumn(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp)
                    .weight(1f, false)
            ) {
                items(items = entryPairs) { item ->
                    val isSelected = rememberSaveable(selected) {
                        mutableStateOf(selected.contains(item.first))
                    }

                    val checkbox = @Composable {
                        Checkbox(
                            checked = isSelected.value,
                            onCheckedChange = {
                                selected = if (it) selected.plus(item.first)
                                else selected.minus(item.first)
                            },
                            modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary,
                                uncheckedColor = MaterialTheme.colorScheme.onSurface
                            )
                        )
                    }

                    Row( // TODO abstract to MultiSelectionListItem
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selected = if (isSelected.value) selected.minus(item.first)
                                else selected.plus(item.first)
                            },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (pref.withIcons) iconIds[item.first]?.let {
                            Icon(
                                modifier = Modifier.size(32.dp),
                                painter = painterResource(id = it),
                                contentDescription = stringResource(id = item.second),
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                        }
                        else checkbox()
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(id = item.second),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (pref.withIcons) checkbox()
                    }
                }
            }

            Row(
                Modifier.fillMaxWidth()
            ) {
                DialogNegativeButton(
                    cornerRadius = cornerRadius,
                    onClick = {
                        openDialogCustom.value = false
                    }
                )
                Spacer(Modifier.weight(1f))
                DialogPositiveButton(
                    cornerRadius = cornerRadius,
                    onClick = {
                        pref.onSetValue(selected)
                        openDialogCustom.value = false
                    }
                )
            }
        }
    }
}
