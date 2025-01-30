package com.amozeshgam.amozeshgam.view.screens.home.roadMap.field

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.amozeshgam.amozeshgam.R
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleField
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSingleFieldRequirement
import com.amozeshgam.amozeshgam.data.model.remote.ApiResponseSinglePageSubFields
import com.amozeshgam.amozeshgam.handler.NavigationHandler
import com.amozeshgam.amozeshgam.handler.UiHandler
import com.amozeshgam.amozeshgam.view.items.FieldAndSubFieldItem
import com.amozeshgam.amozeshgam.view.ui.theme.AmozeshgamTheme
import com.amozeshgam.amozeshgam.viewmodel.home.roadMap.FieldViewModel
import java.text.DecimalFormat

@Composable
fun ViewField(viewModel: FieldViewModel = hiltViewModel(), navController: NavController) {
    val tabs = viewModel.getFieldItems()
    val isLoading = remember {
        mutableStateOf(true)
    }
    val fieldsData = remember {
        mutableStateOf<ApiResponseSingleField?>(null)
    }
    val fieldsId = remember {
        navController.currentBackStackEntry?.arguments?.getInt("id") ?: 0
    }
    UiHandler.ContentWithLoading(
        loading = isLoading.value,
        ifForShowContent = fieldsData.value != null,
        worker = {
            fieldsData.value = viewModel.getFieldData(fieldsId).await()
            isLoading.value = false
            true
        }, onDismissRequestForDefaultErrorHandler = {
            navController.popBackStack()
        }
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .padding(5.dp)
                    .aspectRatio(16f / 9f)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = null,
                    contentDescription = null
                )
            }
            UiHandler.ViewTabAndPager(
                modifier = Modifier.fillMaxWidth(),
                modifierPager = Modifier.fillMaxSize(),
                modifierTabRow = Modifier
                    .fillMaxWidth(), tabs = tabs.toTypedArray(), initializePage = tabs.lastIndex
            ) {
                when (it) {
                    0 -> ViewPrerequisites(requirement = fieldsData.value!!.requirement)
                    else -> ViewSubField(navController = navController, fieldsData.value!!.subField)
                }
            }
        }
    }
}

@Composable
fun ViewPrerequisites(
    viewModel: FieldViewModel = hiltViewModel(),
    requirement: ApiResponseSingleFieldRequirement?,
) {
    requirement?.let {
        val addRequirementLoading = remember {
            mutableStateOf(false)
        }
        val context = LocalContext.current
        val lifecycle = LocalLifecycleOwner.current
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .padding(5.dp)
                        .aspectRatio(16f / 9f)
                ) {
                    AsyncImage(
                        modifier = Modifier.fillMaxSize(),
                        model = null,
                        contentDescription = null
                    )
                }
                Text(
                    text = it.description,
                    textAlign = TextAlign.End,
                    fontFamily = FontFamily(Font(R.font.yekan_bakh_regular)),
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(10.dp),
                    text = "از کی یاد می گیریم؟",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Black,
                    fontFamily = AmozeshgamTheme.fonts["black"],
                    color = AmozeshgamTheme.colors["textColor"]!!
                )
                AsyncImage(
                    modifier = Modifier
                        .padding(10.dp)
                        .aspectRatio(2.79f / 1.25f)
                        .clip(RoundedCornerShape(10.dp))
                        .clickable {
                        },
                    model = null,
                    contentDescription = null,
                    contentScale = ContentScale.Fit
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f)
                            .height(45.dp)
                            .shadow(
                                10.dp,
                                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                                spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        elevation = CardDefaults.cardElevation(5.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!)
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            UiHandler.AnythingRow(
                                modifier = Modifier.align(Alignment.Center),
                                itemOne = {
                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                        Text(
                                            text = "${requirement.time} ساعت ",
                                            color = AmozeshgamTheme.colors["textColor"]!!,
                                            fontFamily = FontFamily(
                                                Font(R.font.yekan_bakh_regular)
                                            )
                                        )
                                    }
                                },
                                itemTwo = {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_clock),
                                        contentDescription = null,
                                        tint = AmozeshgamTheme.colors["textColor"]!!
                                    )
                                })
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .weight(1f)
                            .height(45.dp)
                            .shadow(
                                10.dp,
                                ambientColor = AmozeshgamTheme.colors["shadowColor"]!!,
                                spotColor = AmozeshgamTheme.colors["shadowColor"]!!,
                                shape = RoundedCornerShape(10.dp)
                            ),
                        elevation = CardDefaults.cardElevation(5.dp),
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(containerColor = AmozeshgamTheme.colors["background"]!!)
                    ) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            UiHandler.AnythingRow(
                                modifier = Modifier.align(Alignment.Center),
                                itemOne = {
                                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                                        Text(
                                            text = "${DecimalFormat(",000").format(requirement.price.toInt())} تومان ",
                                            color = AmozeshgamTheme.colors["textColor"]!!,
                                            fontFamily = FontFamily(
                                                Font(R.font.yekan_bakh_regular)
                                            )
                                        )
                                    }
                                },
                                itemTwo = {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_dolar),
                                        contentDescription = null,
                                    )
                                })
                        }
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    onClick = {
                        if (!addRequirementLoading.value) {
                            addRequirementLoading.value = true
                            viewModel.addRequirementToMyCart(id = requirement.id)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = AmozeshgamTheme.colors["primary"]!!),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    if (addRequirementLoading.value) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = "افزودن به سبد خرید",
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.yekan_bakh_regular))
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.ic_cart),
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
                LaunchedEffect(Unit) {
                    viewModel.addedRequirementToMyCart.observe(lifecycle) {
                        addRequirementLoading.value = false
                        if (it) {
                            Toast.makeText(context, "اضافه شد", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "خطا در ثبت دوره", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ViewSubField(
    navController: NavController,
    subFieldData: ArrayList<ApiResponseSinglePageSubFields>,
) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(2),
    ) {
        items(subFieldData.size) { index ->
            FieldAndSubFieldItem(
                modifier = Modifier.padding(7.dp),
                imageUrl = subFieldData[index].image,
                text = subFieldData[index].title
            ) {
                navController.navigate(NavigationHandler.SingleSubFieldScreen.route + "/" + subFieldData[index].id)
            }
        }
    }
}