package com.amozeshgam.amozeshgam.handler

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.BlurMaskFilter
import android.icu.text.DecimalFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.Popup
import androidx.core.app.PictureInPictureModeChangedInfo
import androidx.core.text.isDigitsOnly
import androidx.core.util.Consumer
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.db.IO.DataBaseInputOutput
import com.amozeshgam.amozeshgam.data.db.key.DataStoreKey
import com.amozeshgam.amozeshgam.data.model.local.GlobalUiModel
import com.amozeshgam.amozeshgam.data.repository.HomeClusterRepository
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.HomeViewModel
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object UiHandler {
    fun changeTheme(themeCode: Int, dataBaseInputOutput: DataBaseInputOutput?) {
        GlobalUiModel.uiTheme.intValue = themeCode
        dataBaseInputOutput?.let {
            dataBaseInputOutput.saveData {
                it[DataStoreKey.themeDataKey] = themeCode
            }
        }
    }

    @Composable
    fun OutLineTextField(
        modifier: Modifier = Modifier,
        shape: Shape = RoundedCornerShape(10.dp),
        value: String = "",
        onValueChange: (newValue: String) -> Unit = {},
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        label: @Composable () -> Unit,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        enabled: Boolean = true,
        singleLine: Boolean = false,
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            OutlinedTextField(
                modifier = modifier,
                enabled = enabled,
                singleLine = singleLine,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AmozeshgamTheme.colors["focusBorderColor"]!!,
                    unfocusedBorderColor = AmozeshgamTheme.colors["borderColor"]!!,
                    errorLabelColor = AmozeshgamTheme.colors["errorColor"]!!,
                    focusedLabelColor = AmozeshgamTheme.colors["focusBorderColor"]!!,
                    focusedTextColor = AmozeshgamTheme.colors["textColor"]!!,
                    unfocusedLabelColor = AmozeshgamTheme.colors["textColor"]!!
                ),
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                shape = shape,
                value = value,
                label = label,
                onValueChange = {
                    onValueChange(it)
                },
                keyboardOptions = keyboardOptions
            )
        }
    }

    @Composable
    fun CustomTextField(
        modifier: Modifier = Modifier,
        value: String,
        placeholder: String = "",
        onValueChange: (newValue: String) -> Unit,
        shape: Shape = TextFieldDefaults.shape,
        keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
        enabled: Boolean = true,
        leadingIcon: @Composable (() -> Unit)? = null,
        trailingIcon: @Composable (() -> Unit)? = null,
        readOnly: Boolean = false,
    ) {
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            TextField(
                modifier = modifier,
                placeholder = {
                    Text(text = placeholder)
                },
                value = value,
                onValueChange = {
                    onValueChange(it)
                },
                readOnly = readOnly,
                shape = shape,
                keyboardOptions = keyboardOptions,
                enabled = enabled,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AmozeshgamTheme.colors["textColor"]!!,
                    unfocusedTextColor = AmozeshgamTheme.colors["textColor"]!!,
                    focusedContainerColor = AmozeshgamTheme.colors["background"]!!,
                    unfocusedContainerColor = AmozeshgamTheme.colors["background"]!!,
                    disabledTextColor = AmozeshgamTheme.colors["borderColor"]!!,
                    disabledContainerColor = AmozeshgamTheme.colors["borderColor"]!!,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
            )
        }
    }

    @Composable
    fun AnythingRow(
        modifier: Modifier = Modifier,
        itemOne: @Composable () -> Unit,
        itemTwo: @Composable () -> Unit,
    ) {
        Row(
            modifier = modifier, verticalAlignment = Alignment.CenterVertically
        ) {
            itemOne()
            itemTwo()
        }
    }


    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun ContentWithLoading(
        loading: Boolean = true,
        ifForShowContent: Boolean = true,
        worker: suspend () -> Boolean = { true },
        navController: NavController? = null,
        onDismissRequestForDefaultErrorHandler: () -> Unit = {},
        errorContent: @Composable () -> Unit = {
            val isLoading = remember {
                mutableStateOf(false)
            }
            val context = LocalContext.current
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AmozeshgamTheme.colors["background"]!!)
            ) {
                ErrorDialog(
                    imageId = R.drawable.ic_network_error,
                    text = "لطفا دسترسی به اینترنت را بررسی کنید",
                    onDismiss = onDismissRequestForDefaultErrorHandler
                ) {
                    if (isLoading.value) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = AmozeshgamTheme.colors["primary"]!!
                        )
                    } else {
                        Text(
                            modifier = Modifier.clickable {
                                if (!isLoading.value) {
                                    isLoading.value = true
                                    CoroutineScope(Dispatchers.IO).launch {
                                        isLoading.value = !worker()
                                    }
                                }
                            },
                            text = "تلاش مجدد",
                            color = AmozeshgamTheme.colors["primary"]!!
                        )
                    }
                    if (navController != null) {
                        Text(modifier = Modifier.clickable {
                            navController.popBackStack()
                        }, text = "خروج", color = AmozeshgamTheme.colors["errorColor"]!!)
                    } else {
                        Text(
                            modifier = Modifier.clickable {
                                context.startActivity(
                                    Intent(Settings.ACTION_WIFI_SETTINGS)
                                )
                            },
                            text = "رفتن به تنظیمات",
                            color = AmozeshgamTheme.colors["primary"]!!
                        )
                    }
                }
            }
        },
        content: @Composable () -> Unit,
    ) {
        AmozeshgamTheme(
            darkTheme = themeState()
        ) {
            if (loading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(AmozeshgamTheme.colors["background"]!!)
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = AmozeshgamTheme.colors["primary"]!!
                    )
                    rememberCoroutineScope().launch {
                        worker()
                    }
                }
            } else {
                if (ifForShowContent) {
                    content()
                } else {
                    errorContent()
                }
            }
        }
    }


    @Composable
    fun ClipItem(image: Int, text: String, onClick: () -> Unit = {}) {
        Row(
            modifier = Modifier
                .padding(horizontal = 5.dp)
                .border(
                    1.dp, AmozeshgamTheme.colors["borderColor"]!!, RoundedCornerShape(7.dp)
                )
                .background(AmozeshgamTheme.colors["background"]!!)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .height(20.dp),
                text = text,
                color = AmozeshgamTheme.colors["textColor"]!!,
                fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
            )
            Image(
                modifier = Modifier
                    .padding(horizontal = 3.dp)
                    .size(20.dp),
                painter = painterResource(id = image),
                contentDescription = null,
            )
        }
    }

    @Composable
    fun ClipItem(
        modifier: Modifier = Modifier,
        composable: @Composable RowScope.() -> Unit,
        onClick: () -> Unit = {},
    ) {
        Row(
            modifier = modifier
                .padding(horizontal = 5.dp)
                .border(
                    1.dp, AmozeshgamTheme.colors["primary"]!!, RoundedCornerShape(7.dp)
                )
                .background(AmozeshgamTheme.colors["background"]!!)
                .padding(8.dp)
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            content = composable
        )

    }

    @Composable
    fun CustomDialog(onDismiss: () -> Unit = {}, content: @Composable BoxScope.() -> Unit) {
        Dialog(onDismissRequest = onDismiss) {
            Box(
                modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(AmozeshgamTheme.colors["dialogBackground"]!!),
                content = content
            )
        }
    }

    @Composable
    fun ErrorDialog(
        onDismiss: () -> Unit = {},
        @DrawableRes imageId: Int,
        text: String,
        buttons: @Composable RowScope.() -> Unit = {},
    ) {
        CustomDialog(
            onDismiss = onDismiss
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(170.dp)
                    .padding(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.size(70.dp),
                    painter = painterResource(id = imageId),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Text(
                    text = text,
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    maxLines = 1,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_bold))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = buttons
                )
            }
        }
    }

    @Composable
    fun AlertDialog(
        onDismiss: () -> Unit = {},
        imageId: Int,
        title: String,
        description: String,
        buttons: @Composable RowScope.() -> Unit = {},
    ) {
        CustomDialog(
            onDismiss = onDismiss
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .verticalScroll(
                        rememberScrollState()
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.padding(12.dp),
                    painter = painterResource(id = imageId),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Right,
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_bold))
                )
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = description,
                    textAlign = TextAlign.Right,
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    content = buttons
                )
            }
        }
    }

    @Composable
    fun ViewTabAndPager(
        modifier: Modifier = Modifier,
        modifierTabRow: Modifier = modifier,
        modifierPager: Modifier = Modifier,
        tabs: Array<String>,
        initializePage: Int = 0,
        pager: @Composable (index: Int) -> Unit,
    ) {
        val pagerState = rememberPagerState(
            initialPage = initializePage
        ) {
            tabs.size
        }
        val coroutine = rememberCoroutineScope()
        Column(
            modifier = modifier,
        ) {
            TabRow(
                modifier = modifierTabRow,
                selectedTabIndex = pagerState.currentPage,
                divider = {
                    HorizontalDivider(
                        modifier = Modifier.shadow(
                            10.dp,
                            ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                            spotColor = AmozeshgamTheme.colors["shadowColor"]!!
                        ),
                        color = AmozeshgamTheme.colors["background"]!!
                    )
                },
                indicator = {
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(it[pagerState.currentPage]),
                        color = AmozeshgamTheme.colors["primary"]!!
                    )
                },
                containerColor = AmozeshgamTheme.colors["background"]!!,
            ) {
                repeat(tabs.size) { index ->
                    Tab(selected = false, onClick = {
                        coroutine.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = tabs[index],
                            color = AmozeshgamTheme.colors["textColor"]!!,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            fontFamily = FontFamily(Font(R.font.yekan_bakh_black))
                        )
                    }
                }

            }
            HorizontalPager(modifier = modifierPager, state = pagerState) {
                pager(it)
            }
        }
    }

    @Composable
    fun themeState(): Boolean {
        return when (GlobalUiModel.uiTheme.intValue) {
            1 -> isSystemInDarkTheme()
            2 -> false
            3 -> true
            else -> false
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun DropDownMenuOption(
        modifier: Modifier = Modifier,
        items: List<String>,
        value: String?,
        hint: String = "",
        onValueChange: (newIndex: Int) -> Unit,
    ) {
        val expanded = remember {
            mutableStateOf(false)
        }
        ExposedDropdownMenuBox(modifier = modifier,
            expanded = expanded.value,
            onExpandedChange = { expanded.value = !expanded.value }) {
            TextField(
                modifier = Modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
                value = if (value.isNullOrEmpty()) "" else value,
                onValueChange = {},
                readOnly = true,
                placeholder = {
                    Text(text = hint)
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = AmozeshgamTheme.colors["textColor"]!!,
                    unfocusedTextColor = AmozeshgamTheme.colors["textColor"]!!,
                    focusedContainerColor = AmozeshgamTheme.colors["background"]!!,
                    unfocusedContainerColor = AmozeshgamTheme.colors["background"]!!,
                    disabledTextColor = AmozeshgamTheme.colors["borderColor"]!!,
                    disabledContainerColor = AmozeshgamTheme.colors["borderColor"]!!,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                containerColor = AmozeshgamTheme.colors["background"]!!,
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }) {
                items.forEachIndexed { index, value ->
                    DropdownMenuItem(text = {
                        Text(
                            text = value,
                            color = AmozeshgamTheme.colors["textColor"]!!
                        )
                    },
                        onClick = {
                            expanded.value = false
                            onValueChange(index)
                        })
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun WalletChargeBottomSheet(
        modifier: Modifier = Modifier,
        onDismissRequest: () -> Unit,
        suggestionList: Array<Int>,
        onChargeButtonClick: (value: Int) -> Unit,
    ) {
        val text = remember {
            mutableStateOf("")
        }
        val context = LocalContext.current
        ModalBottomSheet(
            modifier = modifier
                .fillMaxWidth()
                .height(330.dp),
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,

                ),
            tonalElevation = 10.dp,
            containerColor = AmozeshgamTheme.colors["background"]!!,
            onDismissRequest = onDismissRequest
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(horizontal = 10.dp),
                    text = "مبالغ پیشنهادی",
                    color = AmozeshgamTheme.colors["textColor"]!!,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(suggestionList.size) { index ->
                        ClipItem(
                            modifier = modifier.padding(2.dp),
                            composable = {
                                Text(
                                    text = "تومان",
                                    color = AmozeshgamTheme.colors["primary"]!!,
                                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                                    fontSize = 16.sp
                                )
                                Text(
                                    text = DecimalFormat(",000").format(suggestionList[index]),
                                    color = AmozeshgamTheme.colors["primary"]!!,
                                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                                    fontSize = 16.sp
                                )
                            },
                        ) {
                            text.value =
                                suggestionList[index].toString()
                        }
                    }
                }
                OutLineTextField(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = "وارد کردن مبلغ دلخواه",
                        )
                    },
                    value = text.value,
                    onValueChange = {
                        text.value = it
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )
                Button(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    onClick = {
                        if (text.value.isDigitsOnly()) {
                            onChargeButtonClick(text.value.toInt())
                        } else {
                            Toast.makeText(context, "مبلغ نامعتبر", Toast.LENGTH_SHORT).show()
                        }
                    },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["primary"]!!)
                ) {
                    Text(
                        text = "شارژ کیف پول", color = Color.White
                    )
                }
            }
        }
    }

    @Composable
    fun WalletCardProfile(
        value: Int, onTransactionClick: () -> Unit = {}, onChargingClick: () -> Unit = {},
    ) {
        val textValue = remember {
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontSize = 25.sp, color = Color.White)) {
                    if (value > 0) {
                        append(DecimalFormat(",000").format(value))
                    } else {
                        append(" 0 ")
                    }
                }
                withStyle(style = SpanStyle(fontSize = 15.sp, color = Color.White)) {
                    append("تومان")
                }
            }
        }
        Card(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth()
                .height(150.dp)
                .shadow(
                    20.dp,
                    shape = RoundedCornerShape(10.dp),
                    ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                    spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                ),
            colors = CardDefaults.cardColors(containerColor = if (themeState()) AmozeshgamTheme.colors["background"]!! else AmozeshgamTheme.colors["primary"]!!),
            elevation = CardDefaults.cardElevation(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        modifier = Modifier.padding(5.dp),
                        painter = painterResource(R.drawable.ic_app_logo_white),
                        contentDescription = null
                    )
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = "موجودی", fontSize = 15.sp, color = Color.White)
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Text(text = textValue)
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        modifier = Modifier
                            .padding(5.dp)
                            .weight(1f),
                        onClick = onTransactionClick,
                        shape = RoundedCornerShape(7.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (themeState()) AmozeshgamTheme.colors["primary"]!! else AmozeshgamTheme.colors["background"]!!),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        Text("تراکنش ها", color = AmozeshgamTheme.colors["iconColor"]!!)
                        Icon(
                            painter = painterResource(R.drawable.ic_transactions),
                            contentDescription = null,
                            tint = AmozeshgamTheme.colors["iconColor"]!!
                        )
                    }
                    Button(
                        modifier = Modifier
                            .padding(5.dp)
                            .weight(1f),
                        onClick = onChargingClick,
                        shape = RoundedCornerShape(7.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = if (themeState()) AmozeshgamTheme.colors["primary"]!! else AmozeshgamTheme.colors["background"]!!),
                        contentPadding = PaddingValues(10.dp)
                    ) {
                        Text("شارژ کیف پول", color = AmozeshgamTheme.colors["iconColor"]!!)
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = null,
                            tint = AmozeshgamTheme.colors["iconColor"]!!
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun DropdownList(
        modifier: Modifier = Modifier, title: String, view: @Composable ColumnScope.() -> Unit,
    ) {
        val expanded = remember {
            mutableStateOf(false)
        }
        Box(modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = AmozeshgamTheme.colors["borderColor"]!!,
                shape = RoundedCornerShape(10.dp)
            )
            .animateContentSize()
            .clickable {
                expanded.value = !expanded.value
            }) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                        .background(if (expanded.value) AmozeshgamTheme.colors["itemColor"]!! else AmozeshgamTheme.colors["background"]!!)
                        .padding(horizontal = 5.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painter = painterResource(if (!expanded.value) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up_dropdown),
                        contentDescription = null,
                        tint = AmozeshgamTheme.colors["textColor"]!!
                    )
                    Text(
                        text = title,
                        fontFamily = AmozeshgamTheme.fonts["bold"],
                        color = AmozeshgamTheme.colors["textColor"]!!
                    )
                }
                if (expanded.value) {
                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth(),
                        thickness = 1.dp,
                        color = AmozeshgamTheme.colors["borderColor"]!!
                    )
                    view()
                }
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @Composable
    fun ContentWithShimmerLoading(
        modifier: Modifier = Modifier,
        loading: Boolean,
        worker: suspend () -> Unit = {},
        content: @Composable (modifier: Modifier) -> Unit,
    ) {
        content(
            if (loading) modifier.shimmer() else modifier
        )
        rememberCoroutineScope().launch {
            worker()
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun ContentWithScaffold(
        modifier: Modifier = Modifier,
        title: String,
        onBackButtonClick: () -> Unit = {},
        navigationIcon: @Composable () -> Unit = {},
        actionIcon: @Composable RowScope.() -> Unit = {},
        content: @Composable (it: PaddingValues) -> Unit,
    ) {
        AmozeshgamTheme(
            darkTheme = themeState(),
        ) {
            Scaffold(modifier = modifier, topBar = {

                TopAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            50.dp,
                            spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                            ambientColor = AmozeshgamTheme.colors["shadowColor"]!!
                        ),
                    title = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = title,
                            textAlign = TextAlign.End,
                            color = AmozeshgamTheme.colors["textColor"]!!,
                            fontFamily = FontFamily(Font(R.font.yekan_bakh_bold))
                        )
                    },
                    actions = {
                        IconButton(
                            modifier = Modifier.padding(horizontal = 5.dp),
                            onClick = onBackButtonClick
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_right),
                                contentDescription = null,
                                tint = AmozeshgamTheme.colors["textColor"]!!
                            )
                        }
                        actionIcon()
                    },
                    navigationIcon = navigationIcon,
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = AmozeshgamTheme.colors["background"]!!)
                )
            }) {
                content(it)
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TourModalBottomSheet(
        modifier: Modifier = Modifier,
        state: SheetState,
        title: String,
        body: String,
        totalStep: Int,
        currentState: Int,
        textButton: String,
        onBackClick: () -> Unit,
        onClick: () -> Unit,
    ) {
        ModalBottomSheet(
            modifier = modifier,
            onDismissRequest = { /*TODO*/ },
            sheetState = state,
            dragHandle = null,
            properties = ModalBottomSheetProperties(
                shouldDismissOnBackPress = false
            ),
            tonalElevation = 10.dp,
            containerColor = AmozeshgamTheme.colors["dialogBackground"]!!
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Row {
                        repeat(totalStep) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(3.dp)
                                    .size(10.dp)
                                    .clip(RoundedCornerShape(100))
                                    .background(if (index == currentState) AmozeshgamTheme.colors["primary"]!! else AmozeshgamTheme.colors["stepColor"]!!)
                            )
                        }
                    }
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(
                        Font(R.font.yekan_bakh_bold)
                    ),
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = body,
                    textAlign = TextAlign.Right,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f)
                            .height(60.dp),
                        onClick = onClick,
                        colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["primary"]!!),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(text = textButton, color = Color.White)
                    }

                    AnimatedVisibility(visible = currentState != 0) {
                        Button(
                            modifier = Modifier
                                .padding(10.dp)
                                .weight(1f)
                                .height(60.dp),
                            onClick = onBackClick,
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            border = BorderStroke(
                                1.dp,
                                color = AmozeshgamTheme.colors["primary"]!!
                            ),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(text = "قبلی", color = AmozeshgamTheme.colors["primary"]!!)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun KeyboardIsOpened(keyboardOpened: MutableState<Boolean>) {
        val view = LocalView.current
        DisposableEffect(Unit) {
            val listener =
                OnGlobalLayoutListener {
                    keyboardOpened.value = ViewCompat.getRootWindowInsets(view)
                        ?.isVisible(WindowInsetsCompat.Type.ime()) ?: true
                }
            view.viewTreeObserver.addOnGlobalLayoutListener(listener)
            onDispose {
                view.viewTreeObserver.removeOnGlobalLayoutListener(listener)
            }
        }
    }

    @SuppressLint("CoroutineCreationDuringComposition")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun HomeScaffoldPattern(
        navController: NavController,
        viewModel: HomeViewModel = hiltViewModel(),
        showDefaultTopAppBar: Boolean = true,
        showDefaultBottomBar: Boolean = true,
        content: @Composable (navController: NavController) -> Unit
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        val navItems = viewModel.getNavItems()
        val showMenuItem = remember {
            mutableStateOf(false)
        }
        val snackHostState = remember {
            SnackbarHostState()
        }
        val showRecipientNotificationDialog = remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        val dontShowGetPermissionDialogAgain = remember {
            mutableStateOf(false)
        }
        AmozeshgamTheme(
            darkTheme = UiHandler.themeState()
        ) {
            Scaffold(
                topBar = {
                    if (showDefaultTopAppBar) {
                        TopAppBar(
                            modifier = Modifier.shadow(
                                50.dp,
                                spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!
                            ),
                            title = {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .fillMaxHeight()
                                            .padding(12.dp),
                                        painter = painterResource(id = AmozeshgamTheme.assets["amozeshgamBanner"]!!),
                                        contentDescription = null,
                                        contentScale = ContentScale.FillHeight
                                    )

                                }
                            },
                            navigationIcon = {
                                IconButton(modifier = Modifier.size(67.dp), onClick = {
                                    navController.navigate(NavigationScreenHandler.NotificationScreen.route)
                                }) {
                                    if (GlobalUiModel.numberOfMessage.intValue != 0) {
                                        BadgedBox(badge = {
                                            Badge(
                                                modifier = Modifier.padding(end = 10.dp),
                                                containerColor = AmozeshgamTheme.colors["badgeBoxColor"]!!,
                                                contentColor = AmozeshgamTheme.colors["primary"]!!
                                            ) {
                                                Text(
                                                    text = GlobalUiModel.numberOfMessage.intValue.toString(),
                                                    color = Color.White
                                                )
                                            }
                                        }) {
                                            Icon(
                                                modifier = Modifier.padding(5.dp),
                                                painter = painterResource(id = R.drawable.ic_notification),
                                                contentDescription = null,
                                                tint = AmozeshgamTheme.colors["textColor"]!!
                                            )
                                        }
                                    } else {
                                        Icon(
                                            modifier = Modifier.padding(5.dp),
                                            painter = painterResource(id = R.drawable.ic_notification),
                                            contentDescription = null,
                                            tint = AmozeshgamTheme.colors["textColor"]!!
                                        )
                                    }
                                }
                            },
                            actions = {
                                if (currentRoute == NavigationScreenHandler.HomeScreen.route) {
                                    IconButton(onClick = {
                                        showMenuItem.value = true
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_more),
                                            contentDescription = null,
                                            tint = AmozeshgamTheme.colors["textColor"]!!
                                        )
                                    }
                                } else {
                                    IconButton(onClick = {
                                        viewModel.clearAllExoplayer()
                                        navController.popBackStack()
                                    }) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_arrow_right),
                                            contentDescription = null,
                                            tint = AmozeshgamTheme.colors["textColor"]!!
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = AmozeshgamTheme.colors["background"]!!)
                        )
                    }
                },
                bottomBar = {
                    if (showDefaultBottomBar) {
                        BottomAppBar(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(55.dp)
                                .shadow(
                                    color = AmozeshgamTheme.colors["shadowColor"]!!,
                                    offsetX = 4.dp,
                                    offsetY = 4.dp,
                                    blurRadius = 8.dp
                                )
                                .shadow(5.dp),

                            containerColor = AmozeshgamTheme.colors["background"]!!,
                            tonalElevation = 10.dp,
                        ) {
                            repeat(navItems.size) { index ->
                                NavigationBarItem(selected = false, onClick = {
                                    navController.navigate(navItems[index].route)
                                }, icon = {
                                    if (index == 2 && GlobalUiModel.numberOfCart.intValue != 0) {
                                        BadgedBox(badge = {
                                            Badge(
                                                containerColor = AmozeshgamTheme.colors["badgeBoxColor"]!!,
                                                contentColor = AmozeshgamTheme.colors["primary"]!!
                                            ) {
                                                Text(
                                                    text = GlobalUiModel.numberOfCart.intValue.toString(),
                                                    color = Color.White
                                                )
                                            }
                                        }) {
                                            Icon(
                                                painter = painterResource(
                                                    id = if (navItems[index].isSelected(
                                                            currentRoute.toString()
                                                        )
                                                    ) navItems[index].selectedIcon else navItems[index].unSelectedIcon
                                                ),
                                                contentDescription = null,
                                                tint = if (navItems[index].isSelected(currentRoute.toString())) AmozeshgamTheme.colors["primary"]!! else AmozeshgamTheme.colors["textColor"]!!
                                            )
                                        }
                                    } else {
                                        Icon(
                                            painter = painterResource(
                                                id = if (navItems[index].isSelected(
                                                        currentRoute.toString()
                                                    )
                                                ) navItems[index].selectedIcon else navItems[index].unSelectedIcon
                                            ),
                                            contentDescription = null,
                                            tint = if (navItems[index].isSelected(currentRoute.toString())) AmozeshgamTheme.colors["primary"]!! else AmozeshgamTheme.colors["textColor"]!!
                                        )
                                    }
                                })
                            }
                        }
                    }
                },
                snackbarHost = {
                    SnackbarHost(snackHostState) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Snackbar(
                                actionColor = AmozeshgamTheme.colors["background"]!!,
                                snackbarData = it,
                                containerColor = AmozeshgamTheme.colors["textColor"]!!,
                                contentColor = AmozeshgamTheme.colors["background"]!!
                            )
                        }
                    }
                },
                containerColor = AmozeshgamTheme.colors["background"]!!
            ) { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    content(navController)
                }
                if (showMenuItem.value) {
                    Popup(
                        onDismissRequest = {
                            showMenuItem.value = false
                        },
                        alignment = Alignment.TopEnd,
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(padding)
                                .shadow(5.dp, shape = RoundedCornerShape(10.dp))
                                .background(color = AmozeshgamTheme.colors["background"]!!)
                                .width(200.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .align(Alignment.TopEnd),
                                horizontalAlignment = Alignment.End
                            ) {
                                UiHandler.AnythingRow(
                                    modifier = Modifier
                                        .padding(vertical = 5.dp)
                                        .clickable {
                                            showMenuItem.value = false
                                            navController.navigate(NavigationScreenHandler.TicketScreen.route)
                                        },
                                    itemOne = {
                                        Text(
                                            text = "پشتیبانی",
                                            textAlign = TextAlign.Right,
                                            color = AmozeshgamTheme.colors["textColor"]!!,
                                            fontSize = 15.sp,
                                            fontFamily = AmozeshgamTheme.fonts["bold"]
                                        )
                                    },
                                    itemTwo = {
                                        Image(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(2.dp),
                                            painter = painterResource(R.drawable.ic_support),
                                            contentDescription = null
                                        )
                                    }
                                )
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .fillMaxWidth(),
                                    thickness = 1.dp,
                                    color = AmozeshgamTheme.colors["textColor"]!!.copy(alpha = 0.3f)
                                )
                                UiHandler.AnythingRow(
                                    modifier = Modifier
                                        .clickable {
                                            showMenuItem.value = false
                                        },
                                    itemOne = {
                                        Text(
                                            text = "تماس با ما",
                                            textAlign = TextAlign.Right,
                                            color = AmozeshgamTheme.colors["textColor"]!!,
                                            fontSize = 15.sp,
                                            fontFamily = AmozeshgamTheme.fonts["bold"]
                                        )
                                    },
                                    itemTwo = {
                                        Image(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(2.dp),
                                            painter = painterResource(R.drawable.ic_contact_us),
                                            contentDescription = null
                                        )
                                    }
                                )
                                HorizontalDivider(
                                    modifier = Modifier
                                        .padding(5.dp)
                                        .fillMaxWidth(),
                                    thickness = 1.dp,
                                    color = AmozeshgamTheme.colors["textColor"]!!.copy(alpha = 0.3f)
                                )
                                UiHandler.AnythingRow(
                                    modifier = Modifier
                                        .clickable {
                                            showMenuItem.value = false
                                            viewModel.logOut()
                                            navController.navigate(NavigationScreenHandler.LoginScreenOne.route) {
                                                popUpTo(NavigationClusterHandler.Home.route) {
                                                    inclusive = false
                                                }
                                            }
                                        },
                                    itemOne = {
                                        Text(
                                            text = "خروج از حساب",
                                            textAlign = TextAlign.Right,
                                            color = AmozeshgamTheme.colors["textColor"]!!,
                                            fontSize = 15.sp,
                                            fontFamily = AmozeshgamTheme.fonts["bold"]
                                        )
                                    },
                                    itemTwo = {
                                        Image(
                                            modifier = Modifier
                                                .size(20.dp)
                                                .padding(2.dp),
                                            painter = painterResource(R.drawable.ic_logout),
                                            contentDescription = null
                                        )
                                    }
                                )
                            }

                        }
                    }
                }
                if (showRecipientNotificationDialog.value) {
                    UiHandler.AlertDialog(
                        onDismiss = {
                            if (dontShowGetPermissionDialogAgain.value) {
                                viewModel.doNotShowGetPermissionDialog(
                                    true
                                )
                            }
                            showRecipientNotificationDialog.value = false
                        },
                        imageId = AmozeshgamTheme.assets["amozeshgamBanner"]!!,
                        title = "دسترسی خواندن اعلانات",
                        description = "برای بهتر شدن خدمات ارايه شده توسط آموزشگام نیاز است که دسترسی خواندن اعلانات را فعال کنید",
                        buttons = {
                            TextButton(
                                modifier = Modifier.padding(5.dp),
                                onClick = {
                                    showRecipientNotificationDialog.value = false
                                    context.startActivity(Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                                },
                                shape = RoundedCornerShape(10.dp),
                                contentPadding = PaddingValues(10.dp)
                            ) {
                                Text(
                                    text = "فعال کردن",
                                    color = AmozeshgamTheme.colors["primary"]!!,
                                    fontFamily = AmozeshgamTheme.fonts["regular"]
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "دیگر نمایش نده",
                                    color = AmozeshgamTheme.colors["textColor"]!!,
                                    fontFamily = AmozeshgamTheme.fonts["regular"]
                                )
                                Checkbox(
                                    checked = dontShowGetPermissionDialogAgain.value,
                                    onCheckedChange = {
                                        dontShowGetPermissionDialogAgain.value = it
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkmarkColor = AmozeshgamTheme.colors["primary"]!!,
                                        checkedColor = AmozeshgamTheme.colors["background"]!!
                                    )
                                )
                            }
                        }
                    )
                }
                if (GlobalUiModel.errorExceptionDialog.value) {
                    Log.i(
                        "jjj",
                        "HomeScaffoldPattern: ${GlobalUiModel.errorExceptionMessage.value}"
                    )
                    UiHandler.ErrorDialog(
                        imageId = R.drawable.ic_error,
                        text = ".برنامه با خطا مواجه شده است"
                    ) {
                        Text(
                            modifier = Modifier.clickable {
                                viewModel.reportBug(GlobalUiModel.errorExceptionMessage.value)
                                GlobalUiModel.errorExceptionDialog.value = false
                            },
                            text = "اطلاع رسانی",
                            color = AmozeshgamTheme.colors["errorColor"]!!,
                            fontFamily = FontFamily(
                                Font(R.font.yekan_bakh_regular)
                            )
                        )
                        Text(
                            modifier = Modifier.clickable {
                                GlobalUiModel.errorExceptionDialog.value = false
                            },
                            text = "بستن",
                            color = AmozeshgamTheme.colors["primary"]!!,
                            fontFamily = AmozeshgamTheme.fonts["regular"]
                        )
                    }
                }
                if (GlobalUiModel.showNotificationDialog.value) {
                    UiHandler.AlertDialog(
                        imageId = R.drawable.ic_message,
                        title = "",
                        description = ""
                    )
                }
                if (GlobalUiModel.showEmergencyUSBDialog.value) {
                    UiHandler.AlertDialog(
                        imageId = R.drawable.ic_error,
                        title = "خطای امنیتی",
                        description = "شما نمی توانید هنگام کار با اپلیکیشن آموزشگام دستگاه خارجی را به گوشی خود وصل کنید لطفا آن را قطع کنید"
                    )
                }
                LaunchedEffect(GlobalUiModel.requestForEnabledDarkMode.value) {
                    if (GlobalUiModel.requestForEnabledDarkMode.value) {
                        val snackResult = snackHostState.showSnackbar(
                            message = "حالت بهینه رو فعال کن تا شارژت دیرتر تموم بشه\uD83D\uDE0A",
                            actionLabel = "فعال کردن",
                            duration = SnackbarDuration.Long
                        )
                        GlobalUiModel.requestForEnabledDarkMode.value = false
                        when (snackResult) {
                            SnackbarResult.ActionPerformed -> {
                                UiHandler.changeTheme(
                                    themeCode = GlobalUiModel.DARK_CODE, dataBaseInputOutput = null
                                )
                            }

                            else -> Unit
                        }
                    }
                }
                LaunchedEffect(Unit) {
                    val result = viewModel.checkHash()
                    when (result) {
                        RemoteStateHandler.BAD_RESPONSE -> {
                            viewModel.logOut()
                            navController.navigate(NavigationScreenHandler.LoginScreenOne.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                            Toast.makeText(
                                context,
                                "شما از این حساب اخراج شدید",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        else -> Unit

                    }
                }
                LaunchedEffect(Unit) {

                    showRecipientNotificationDialog.value =
                        viewModel.showRecipientNotificationPermissionDialog()
                    Log.i("jjj", "HomeScaffoldPattern: ${showRecipientNotificationDialog.value} ")
                }
            }
        }
    }

    @Composable
    fun rememberIsInPIPMode(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val activity = LocalContext.current.findActivity()
            val pipMode = remember {
                mutableStateOf(activity.isInPictureInPictureMode)
            }
            DisposableEffect(activity) {
                val observer = Consumer<PictureInPictureModeChangedInfo> { info ->
                    pipMode.value = info.isInPictureInPictureMode
                }
                activity.addOnPictureInPictureModeChangedListener(
                    observer
                )
                onDispose { activity.removeOnPictureInPictureModeChangedListener(observer) }
            }
            return pipMode.value
        } else {
            false
        }
    }
}

internal fun Context.findActivity(): ComponentActivity {
    var context = this
    while (context is ContextWrapper) {
        if (context is ComponentActivity) return context
        context = context.baseContext
    }
    throw (RuntimeException("No Activity found in chain of ContextWrappers."))
}

fun Modifier.shadow(
    color: Color = Color.Black,
    offsetX: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
) = then(
    drawBehind {
        drawIntoCanvas { canvas ->
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }
            frameworkPaint.color = color.toArgb()

            val leftPixel = offsetX.toPx()
            val topPixel = offsetY.toPx()
            val rightPixel = size.width + topPixel
            val bottomPixel = size.height + leftPixel

            canvas.drawRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                paint = paint,
            )
        }
    }
)

fun Context.openLink(link: String) {
    try {
        this.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    } catch (_: Exception) {
    }
}