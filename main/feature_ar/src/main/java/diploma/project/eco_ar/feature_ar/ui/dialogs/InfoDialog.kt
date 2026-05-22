package diploma.project.eco_ar.feature_ar.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import diploma.project.eco_ar.core.domain.model.Report
import diploma.project.eco_ar.core.ui.theme.LocalColorTheme
import diploma.project.eco_ar.core.ui.theme.robotoTextStyle
import diploma.project.eco_ar.feature_ar.R
import diploma.project.eco_ar.feature_ar.ui.components.ReportCard

@Composable
fun InfoDialog(
    isShown: Boolean,
    report: Report?,
    onDismiss: () -> Unit,
    onSaveReport: () -> Unit
) {
    if (!isShown || report == null) return

    val colorTheme = LocalColorTheme.current

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorTheme.colorBackground, RoundedCornerShape(16.dp))
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = stringResource(R.string.information),
                style = robotoTextStyle(28.sp),
                color = colorTheme.colorTextDark
            )
            ReportCard(
                modifier = Modifier.fillMaxWidth(),
                report = report,
                onSaveReport = onSaveReport
            )
        }
    }
}