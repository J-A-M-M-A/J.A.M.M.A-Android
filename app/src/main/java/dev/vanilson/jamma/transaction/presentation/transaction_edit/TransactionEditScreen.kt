package dev.vanilson.jamma.transaction.presentation.transaction_edit

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.vanilson.jamma.R
import dev.vanilson.jamma.transaction.domain.Recurrence
import dev.vanilson.jamma.transaction.presentation.components.Calculator
import org.koin.androidx.compose.koinViewModel
import timber.log.Timber
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun TransactionEditScreen(navHostController: NavHostController, transactionId: Int? = null) {

    println(">>> received transactionId: $transactionId")

    val viewModel =
        if (LocalInspectionMode.current) null else koinViewModel<TransactionEditViewModel>()

    val state = viewModel?.state?.collectAsStateWithLifecycle()?.value

    if (transactionId != null) {
        viewModel?.loadTransaction(transactionId)
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                var expanded by remember { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(50))
                        .background(color = MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth(0.5f)
                        .clickable(
                            true,
                            onClick = {
                                expanded = !expanded
                            }
                        ),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center,
                    ) {
                        DropdownMenu(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            shape = RoundedCornerShape(25.dp),
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                        ) {
                            state?.categories?.map { category ->
                                DropdownMenuItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    leadingIcon = {
                                        Text(
                                            text = category.icon,
                                            style = TextStyle(fontSize = 20.sp)
                                        )
                                    },
                                    text = { Text(text = category.name) },
                                    onClick = {
                                        viewModel.updateCategory(category)
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                        val selectedCategory = state?.selectedCategoryUI
                        if (selectedCategory != null) {
                            DropdownRow(
                                text = selectedCategory.name,
                                icon = selectedCategory.icon,
                            )
                        } else {
                            DropdownRow(
                                text = "Select a category",
                                icon = "️🏷️",
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp, 16.dp, 12.dp, 8.dp),
                contentAlignment = Alignment.Center
            ) {
                SingleChoiceSegmentedButton(
                    enabled = state?.isEditing == false,
                    options = listOf("Expense", "Income"),
                    selectedIndex = if (state?.isIncome == true) 1 else 0,
                    onSelectionChanged = { index ->
                        viewModel?.setIsIncome(index == 1)
                    }
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 8.dp, 8.dp, 16.dp),
                contentAlignment = Alignment.Center
            ) {
                val recurrenceList = Recurrence.entries.map { it.displayName }
                SingleChoiceSegmentedButton(
                    options = recurrenceList,
                    selectedIndex = recurrenceList.indexOf(state?.recurrence?.displayName),
                    onSelectionChanged = { index ->
                        viewModel?.setRecurrence(Recurrence.entries[index])
                    }
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "$ ",
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = state?.amountString ?: "0.00",
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = state?.description ?: "",
                    onValueChange = { viewModel?.updateDescription(it) },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        autoCorrectEnabled = true,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done,
                    ),
                    modifier = Modifier.background(MaterialTheme.colorScheme.background),
                    placeholder = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text("add a description", style = MaterialTheme.typography.bodyLarge)
                        }
                    },

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        disabledContainerColor = MaterialTheme.colorScheme.background,
                    )
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp)
                    .clickable {
                        viewModel?.toggleDatePickerVisibility()
                    },
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        "Due date",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        state?.dueDateFormatted ?: "Select a date",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        "Paid",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Switch(
                        checked = state?.paidDate != null,
                        onCheckedChange = { viewModel?.updatePaidDate(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.onSurface,
                            checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom,
            ) {
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Calculator(
                    onButtonClick = { value ->
                        value?.let {
                            viewModel?.updateAmountString(it)
                        }
                    },
                    onBackspace = { viewModel?.amountBackspace() },
                    onClear = { viewModel?.resetAmountString() },
                    onSubmit = {
                        viewModel?.saveTransaction()
                    }
                )
            }
            if (state?.isDatePickerVisible == true) {
                DatePickerModal({
                    it?.let {
                        val instant = Instant.ofEpochMilli(it)
                        viewModel.updateDueDate(
                            LocalDateTime.ofInstant(
                                instant,
                                ZoneId.systemDefault()
                            )
                        )
                    }
                }) {
                    viewModel.toggleDatePickerVisibility()
                }
            }
            if (state?.success == true) {
                AnimationDialog {
                    navHostController.popBackStack()
                }
            }
        }
    }
}

@Composable
fun SingleChoiceSegmentedButton(
    enabled: Boolean = true,
    options: List<String>,
    selectedIndex: Int,
    onSelectionChanged: (Int) -> Unit = {}
) {
    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    onSelectionChanged(index)
                },
                icon = {},
                selected = index == selectedIndex,
                label = { Text(label) },
                enabled = enabled
            )
        }
    }
}

@Composable
fun DropdownRow(
    text: String,
    icon: String
) {
    return Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = icon,
                style = TextStyle(fontSize = 20.sp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f),
            contentAlignment = Alignment.CenterEnd
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = null
            )
        }
    }
}

@Composable
fun AnimationDialog(callBack: () -> Unit = {}) {
    Timber.d(">>> Showing animation dialog")
    Dialog(
        onDismissRequest = { }
    ) {
        val composition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.done_animation),
        )

        val progress by animateLottieCompositionAsState(composition)

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier.fillMaxWidth(0.5f)
        )

        if (progress == 1f) {
            callBack()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

@Composable
@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun TransactionAddScreenPreview() {
    TransactionEditScreen(navHostController = NavHostController(LocalContext.current))
}