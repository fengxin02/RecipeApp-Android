package hu.bme.aut.android.recept.ui.screen

import android.R.attr.contentDescription
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import hu.bme.aut.android.recept.ui.viewmodel.MealViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MealDetailScreen(
    mealId: String?,
    viewModel: MealViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    //get detail meal
    LaunchedEffect(mealId) {
        if (mealId != null) {
            viewModel.loadMealDetail(mealId)
        }
    }

    val meal by viewModel.selectedMeal.collectAsState()


    var isFavourite by remember { mutableStateOf(false) }

    //check if the detail food is favourite
    LaunchedEffect(mealId) {
        if(mealId != null){
            isFavourite = viewModel.isMealFavorite(mealId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = meal?.strMeal ?: "Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    //favourite
                    if(meal != null) {
                        IconButton(onClick = {
                            viewModel.changeFavourite(meal!!)
                            isFavourite = !isFavourite
                        }) {
                            Icon(
                                imageVector = if (isFavourite) {Icons.Default.Favorite }
                                else{ Icons.Default.FavoriteBorder},
                                contentDescription = "Favourite",
                                tint = if (isFavourite) Color.Red else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        if (meal == null) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding),
                contentAlignment = Alignment.Center) {
                //Text("No details available")
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = meal!!.strMealThumb,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    //basic info
                    Text(
                        text = "${meal!!.strArea} | ${meal!!.strCategory}",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    //instructions
                    Text("How to make it", style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = meal!!.strInstructions ?: "No info", style = MaterialTheme.typography.bodyMedium)

                    Spacer(modifier = Modifier.height(24.dp))


                    //ingredients list
                    Text("Ingredients", style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))

                    val ingredients = listOfNotNull(
                        meal!!.strIngredient1, meal!!.strIngredient2, meal!!.strIngredient3,
                        meal!!.strIngredient4, meal!!.strIngredient5, meal!!.strIngredient6, meal!!.strIngredient7,
                        meal!!.strIngredient8, meal!!.strIngredient9, meal!!.strIngredient10, meal!!.strIngredient11, meal!!.strIngredient12,
                        meal!!.strIngredient13, meal!!.strIngredient14, meal!!.strIngredient15, meal!!.strIngredient16,
                        meal!!.strIngredient17, meal!!.strIngredient18, meal!!.strIngredient19, meal!!.strIngredient20
                    ).filter { it.isNotBlank() }

                    val measures = listOfNotNull(
                        meal!!.strMeasure1, meal!!.strMeasure2, meal!!.strMeasure3,
                        meal!!.strMeasure4, meal!!.strMeasure5, meal!!.strMeasure6, meal!!.strMeasure7,
                        meal!!.strMeasure8, meal!!.strMeasure9, meal!!.strMeasure10, meal!!.strMeasure11, meal!!.strMeasure12,
                        meal!!.strMeasure13, meal!!.strMeasure14, meal!!.strMeasure15, meal!!.strMeasure16,
                        meal!!.strMeasure17, meal!!.strMeasure18, meal!!.strMeasure19, meal!!.strMeasure20
                    )

                    ingredients.forEach { ingredient ->
                        val index = ingredients.indexOf(ingredient)
                        val measure = measures[index]
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = "• $ingredient")

                            if (measure.isNotBlank()) {
                                Text(text = measure)
                            }
                        }
                    }
                }
            }
        }
    }
}
