package com.example.project_app.ui.screens.home

import android.net.Uri
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.project_app.R
import com.example.project_app.ui.screens.settings.SettingsViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    settingsViewModel: SettingsViewModel,
    onNavigateToAddRecord: (carId: Int) -> Unit,
    onNavigateToAddCar: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    val car by viewModel.currentCar.collectAsState(initial = null)
    val allCars by viewModel.allCars.collectAsState()
    val state by viewModel.dashboardState.collectAsState()
    val isGarageView by viewModel.isGarageView.collectAsState()
    val isDarkMode by settingsViewModel.isDarkMode.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var expandedCarMenu by remember { mutableStateOf(false) }

    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("th", "TH"))

    if (showDeleteDialog && car != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text(stringResource(R.string.delete_car_title), fontWeight = FontWeight.Bold) },
            text = { Text(stringResource(R.string.delete_car_confirmation)) },
            confirmButton = {
                TextButton(
                    onClick = {
                        car?.let { viewModel.deleteCurrentCar(it.id) }
                        showDeleteDialog = false
                    }
                ) {
                    Text(stringResource(R.string.delete), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text(stringResource(R.string.cancel_btn))
                }
            }
        )
    }

    if (car == null) {
        // ---------------- Empty State ----------------
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(R.string.dashboard_title), fontWeight = FontWeight.Bold) },
                    actions = {
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
            }
        ) { innerPadding ->
            EmptyDashboardState(
                modifier = Modifier.padding(innerPadding),
                onNavigateToAddCar = onNavigateToAddCar
            )
        }
    } else if (allCars.size > 1 && isGarageView) {
        // ---------------- Garage State ----------------
        GarageListScreen(
            allCars = allCars,
            onCarClick = { viewModel.selectCar(it) },
            onNavigateToAddCar = onNavigateToAddCar,
            isDarkMode = isDarkMode,
            onToggleDarkMode = { settingsViewModel.toggleDarkMode() },
            onNavigateToSettings = onNavigateToSettings
        )
    } else {
        // ---------------- Dashboard State ----------------
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("${car!!.brand} ${car!!.model}", fontWeight = FontWeight.Bold)
                    },
                    navigationIcon = {
                        if (allCars.size > 1) {
                            IconButton(onClick = { viewModel.openGarage() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back to Garage")
                            }
                        }
                    },
                    actions = {
                        IconButton(onClick = onNavigateToAddCar) {
                            Box(contentAlignment = Alignment.BottomEnd) {
                                Icon(Icons.Default.DirectionsCar, contentDescription = "Add New Car")
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(12.dp)
                                        .background(MaterialTheme.colorScheme.surface, androidx.compose.foundation.shape.CircleShape),
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.Delete, contentDescription = stringResource(R.string.delete_car))
                        }
                        IconButton(onClick = onNavigateToSettings) {
                            Icon(Icons.Default.Settings, contentDescription = "Settings")
                        }
                    }
                )
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { onNavigateToAddRecord(car!!.id) },
                    icon = { Icon(Icons.Default.Add, "add") },
                    text = { Text(stringResource(R.string.add_record_btn)) },
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        ) { innerPadding ->
            val c = car!!
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // 1. Top Section - Hero Image Card
                Card(
                    modifier = Modifier.fillMaxWidth().height(220.dp),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (!c.imagePath.isNullOrEmpty()) {
                            AsyncImage(
                                model = Uri.parse(c.imagePath),
                                contentDescription = "Car",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.secondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.DirectionsCar,
                                    contentDescription = null,
                                    modifier = Modifier.size(100.dp),
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.3f)
                                )
                            }
                        }

                        // Gradient Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                                        startY = 200f
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "${c.brand} ${c.model}",
                                color = Color.White,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.ExtraBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Speed, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "${NumberFormat.getNumberInstance().format(c.currentMileage)} ${stringResource(R.string.km_unit)}",
                                    color = Color.White,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }

                // 2. Middle Section - Predictive Alert Card (Wow Factor!)
                val alert = state.predictiveAlert
                val cardColor = when (alert.level) {
                    AlertLevel.GOOD -> MaterialTheme.colorScheme.tertiaryContainer
                    AlertLevel.WARNING -> MaterialTheme.colorScheme.secondaryContainer
                    AlertLevel.DANGER -> MaterialTheme.colorScheme.errorContainer
                }
                val iconColor = when (alert.level) {
                    AlertLevel.GOOD -> MaterialTheme.colorScheme.tertiary
                    AlertLevel.WARNING -> MaterialTheme.colorScheme.secondary
                    AlertLevel.DANGER -> MaterialTheme.colorScheme.error
                }
                val icon = when (alert.level) {
                    AlertLevel.GOOD -> Icons.Default.CheckCircle
                    AlertLevel.WARNING -> Icons.Default.Warning
                    AlertLevel.DANGER -> Icons.Default.Warning
                }
                val title = when (alert.level) {
                    AlertLevel.GOOD -> stringResource(R.string.alert_good_title)
                    AlertLevel.WARNING -> stringResource(R.string.alert_warning_title)
                    AlertLevel.DANGER -> stringResource(R.string.alert_danger_title)
                }
                val subText = when (alert.level) {
                    AlertLevel.GOOD -> stringResource(R.string.alert_good_subtitle)
                    AlertLevel.WARNING -> stringResource(R.string.alert_warning_subtitle)
                    AlertLevel.DANGER -> stringResource(R.string.alert_danger_subtitle)
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = cardColor)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(48.dp))
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
                            Text(subText, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                    }
                }

                // 3. Bottom Section - Expense Columns
                Text(stringResource(R.string.expense_summary), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(stringResource(R.string.this_month), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currencyFormatter.format(state.monthlyTotal),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Card(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(stringResource(R.string.this_year), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = currencyFormatter.format(state.yearlyTotal),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                // Recent Transactions
                Text(stringResource(R.string.recent_transactions), fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                if (state.recentTransactions.isEmpty()) {
                    Text(stringResource(R.string.no_records_yet), color = MaterialTheme.colorScheme.onSurfaceVariant)
                } else {
                    state.recentTransactions.forEach { trx ->
                        TransactionRow(trx)
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

@Composable
fun EmptyDashboardState(modifier: Modifier = Modifier, onNavigateToAddCar: () -> Unit) {
    val infiniteTransition = rememberInfiniteTransition(label = "pulse_button")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "button_scale"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.DirectionsCar,
            contentDescription = "No Car Found",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.empty_state_title),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.empty_state_subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            onClick = onNavigateToAddCar,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale
                ),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
            Spacer(modifier = Modifier.width(8.dp))
            Text(stringResource(R.string.add_first_car), fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun TransactionRow(trx: TransactionUI) {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale("th", "TH"))
    val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("th", "TH"))

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (trx.isMaintenance) Icons.Default.Build else Icons.Default.LocalGasStation,
                contentDescription = "Icon",
                tint = if (trx.isMaintenance) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                val titleText = when (trx.title) {
                    "น้ำมันเครื่อง" -> stringResource(R.string.oil_change)
                    "ผ้าเบรก" -> stringResource(R.string.brake_pads)
                    "ยาง" -> stringResource(R.string.tires)
                    "แบตเตอรี่" -> stringResource(R.string.battery)
                    "ค่าน้ำมัน" -> stringResource(R.string.fuel)
                    "ค่าทางด่วน" -> stringResource(R.string.toll)
                    "ค่าที่จอดรถ" -> stringResource(R.string.parking)
                    "อื่นๆ" -> stringResource(R.string.other)
                    else -> trx.title
                }
                Text(titleText, fontWeight = FontWeight.SemiBold)
                Text(formatter.format(Date(trx.dateMillis)), style = MaterialTheme.typography.bodySmall)
            }
            Text(
                text = currencyFormatter.format(trx.amount),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.error
            )
        }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GarageListScreen(
    allCars: List<com.example.project_app.data.local.entity.CarEntity>,
    onCarClick: (Int) -> Unit,
    onNavigateToAddCar: () -> Unit,
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.dashboard_title), fontWeight = FontWeight.Bold) },
                actions = {
                    IconButton(onClick = onNavigateToAddCar) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Icon(Icons.Default.DirectionsCar, contentDescription = "Add New Car")
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(MaterialTheme.colorScheme.surface, androidx.compose.foundation.shape.CircleShape),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    IconButton(onClick = onNavigateToSettings) {
                        Icon(Icons.Default.Settings, contentDescription = "Settings")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(allCars) { car ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clickable { onCarClick(car.id) },
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        if (!car.imagePath.isNullOrEmpty()) {
                            AsyncImage(
                                model = Uri.parse(car.imagePath),
                                contentDescription = "Car Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.secondaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Default.DirectionsCar,
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp),
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.3f)
                                )
                            }
                        }

                        // Gradient Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                                        startY = 100f
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .align(Alignment.BottomStart)
                                .padding(16.dp)
                        ) {
                            Text(
                                text = "${car.brand} ${car.model}",
                                color = Color.White,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${NumberFormat.getNumberInstance().format(car.currentMileage)} ${stringResource(R.string.km_unit)}",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
