package com.example.vyaperclone

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.vyaperclone.databinding.FragmentItemsBinding
import com.example.vyaperclone.databinding.FragmentPurchaseBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ItemsFragment : Fragment() {
    lateinit var binding: FragmentItemsBinding


    private fun addItem(item: Int) {
        if (item == 2) {
            val action = ItemsFragmentDirections.actionNavItemsToAddProductFragment(null)
            findNavController().navigate(action)
        }
    }

    private fun addParty(item: Int) {
        if (item == 0) {
            val action = ItemsFragmentDirections.actionNavItemsToAddNewPartyFragment()
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.show()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.item_menu, menu)

    }

    private val viewModel: ItemsViewModel by viewModels()
    val sharedViewModel: ItemsViewModel by activityViewModels()
    val purchaseSharedViewModel: PurchaseSharedViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {

            setContent {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF3F3F3)),
                ) {
                    Column(
                        modifier = Modifier.background(Color.White)
                    ) {

                        Row(
                            modifier = Modifier
                                .horizontalScroll(rememberScrollState())
                                .padding(bottom = 27.dp, top = 5.dp)
                        ) {
                            Spacer(modifier = Modifier.size(10.dp))
                            TotalInformationCard(
                                name = context.getString(R.string.youll_get),
                                viewModel.totalToGet.value,
                                R.drawable.ic_arrow_downward,
                                Constants.YOUWILLGETINT,
                                { })
                            Spacer(modifier = Modifier.size(10.dp))

                            TotalInformationCard(
                                name = context.getString(R.string.sale_compose),
                                viewModel.totalSale.value,
                                R.drawable.ic_bill,
                                Constants.SALEINT,
                                { })
                            Spacer(modifier = Modifier.size(10.dp))

                            TotalInformationCard(
                                name = context.getString(R.string.youll_give),
                                viewModel.totalToGive.value,
                                R.drawable.ic_upward,
                                Constants.YOUWILLGIVEINT,
                                { })
                            Spacer(modifier = Modifier.size(10.dp))

                            TotalInformationCard(
                                name = context.getString(R.string.purchase_compose),
                                viewModel.totalPurchase.value,
                                R.drawable.ic_shopping_cart,
                                Constants.PURCHASEINT,
                                { })
                            Spacer(modifier = Modifier.size(10.dp))

//                            TotalInformationCard(
//                                name = context.getString(R.string.expense_compose),
//                                viewModel.totalExpenses.value,
//                                R.drawable.ic_wallet,
//                                Constants.PURCHASEINT,
//                                { })
//                            Spacer(modifier = Modifier.size(10.dp))


                        }

                        Selector(viewModel.selectedType.value) {
                            viewModel.changeSelectedType(it)
                        }

                        if (viewModel.selectedType.value == 0 || viewModel.selectedType.value == 2) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                horizontalArrangement = Arrangement.End,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (viewModel.selectedType.value == 0) {
                                    AddButton(
                                        name = context.getString(R.string.new_party_compose),
                                        onClick = { addParty(it) },
                                        type = 0
                                    )
                                } else if (viewModel.selectedType.value == 2) {
                                    AddButton(name = context.getString(R.string.new_item), onClick = {
                                        addItem(it)
                                    }, type = 2)
                                }

                            }
                        } else {

                            TextField(
                                value = viewModel.searchQuery.value,
                                onValueChange = { viewModel.searchQueryChange(it) },
                                colors = TextFieldDefaults.textFieldColors(
                                    backgroundColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    textColor = Color.Black,
                                ),
//                                placeholder = {
//                                    Text(text = context.getString(R.string.search_trans))
//                                },
//                                leadingIcon = {
//                                    Image(
//                                        painter = painterResource(id = R.drawable.ic_search_blue),
//                                        contentDescription = null
//                                    )
//                                }
                            )
                        }
                        Divider(color = Color.LightGray, thickness = 1.dp, startIndent = 10.dp)
                        if (viewModel.selectedType.value == 2) {
                            LazyColumn() {
                                itemsIndexed(
                                    items = viewModel.items.value
                                ) { index, itemEntity ->
                                    ItemCard(itemEntity = itemEntity, onClick = { /*TODO*/ })
                                }
                            }
                        } else if (viewModel.selectedType.value == 0) {
                            LazyColumn() {
                                itemsIndexed(
                                    items = viewModel.transcations.value
                                ) { index, party ->
                                    if (party.type == Constants.PURCHASE || party.type == Constants.SALE || party.type == "Receive" || party.type == "Pay"){
                                        PartiesCard(partyEntity = party, onClick = { /*TODO*/
                                            val action =
                                                PartyDetailsFragmentDirections.actionPartyDetailsFragment()
                                            findNavController().navigate(action)
                                            Constants.AllPartyName = party.partyName.toString()
                                            Constants.PartyType = party.type.toString()
                                            Constants.Amount = party.total.toString()
                                            Constants.ReceivedAmount = party.received.toString()
                                            Constants.PaidAmount = party.paidAmt.toString()
                                            Constants.CONTACTNO = party.partyContactNumber.toString()
                                        })
                                }
                                }
                            }
                            Constants.existPartyName =  viewModel.transcations.value
                        } else if (viewModel.selectedType.value == 1) {
                            LazyColumn() {
                                itemsIndexed(
                                    items = viewModel.transcations.value
                                )
                                { index, transaction ->

                                    if (viewModel.searchQuery.value.isEmpty() && (transaction.type == Constants.PURCHASE || transaction.type == Constants.SALE)) {
                                        TransactionCard(
                                            transactionEntity = transaction,
                                            onClick = { /*TODO*/
                                                // Handle card click
                                                if (transaction.type == "purchase") {
                                                    val action = UpdatePurchaseDataDirections.actionUpdatePurchaseData()
                                                    findNavController().navigate(action)
                                                    Constants.PartyName = transaction.partyName.toString()
                                                    Constants.BillNo = transaction.billNo!!.toInt()
                                                    Constants.TotalAmt = transaction.total!!.toInt()
                                                    Constants.PaidAmt = transaction.paidAmt!!.toInt()

                                                    Constants.itemsName = transaction.billedItemNames.toString()
                                                    Constants.quantity = transaction.billedItemQuantity.toString()
                                                    Constants.rate = transaction.billedItemRate.toString()
                                                    Constants.CONTACTNO = transaction.partyContactNumber.toString()
                                                }
                                                else{
                                                    val action = UpdateSaleFragmentDirections.actionUpdateSaleFragment()
                                                    findNavController().navigate(action)
                                                    Constants.PartyName = transaction.partyName.toString()
                                                    Constants.BillNo = transaction.billNo!!.toInt()
                                                    Constants.TotalAmt = transaction.total!!.toInt()
                                                    Constants.Received = transaction.received!!.toInt()

                                                    Constants.itemsName = transaction.billedItemNames.toString()
                                                    Constants.quantity = transaction.billedItemQuantity.toString()
                                                    Constants.rate = transaction.billedItemRate.toString()
                                                    Constants.CONTACTNO = transaction.partyContactNumber.toString()
                                                }})
                                    } else if (transaction.partyName!!.contains(viewModel.searchQuery.value) && (transaction.type == Constants.PURCHASE || transaction.type == Constants.SALE)){
                                        TransactionCard(
                                            transactionEntity = transaction,
                                            onClick = { /*TODO*/ })
                                    }

                                }
                                Constants.partyDetails = viewModel.transcations.value
                            }
                        }
                    }
                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    BottomButtons(
                        { navigateToPurchaseFragment() },
                        { openBottomSheet() },
                        { navigateToSaleFragment() })
                    Spacer(modifier = Modifier.size(10.dp))
                }





            }
        }
    }

    private fun openBottomSheet() {
        val bottomSheetDialog =
            context?.let { BottomSheetDialog(it, R.style.BottomSheetDialogTheme) }

        val view = layoutInflater.inflate(
            R.layout.bottom_sheet, activity?.findViewById(R.id.llBottomConatainer)
        )
        bottomSheetDialog?.setContentView(view)
        bottomSheetDialog?.setCanceledOnTouchOutside(true)
        bottomSheetDialog?.show()

        val closeButton = bottomSheetDialog?.findViewById<ImageButton>(R.id.closeButton)
        closeButton?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }
        val tvLendMoney = bottomSheetDialog?.findViewById<TextView>(R.id.tvLendMoney)
        tvLendMoney?.setOnClickListener{
           showCustomDialog()
        }
        val tvRecoveryMoney = bottomSheetDialog?.findViewById<TextView>(R.id.tvRecoverMoney)
        tvRecoveryMoney?.setOnClickListener{
            showRecoveryMoneyDialog()
        }

    }

    private fun showRecoveryMoneyDialog() {
        val dialogView = layoutInflater.inflate(R.layout.top_sheet_recovery_money, activity?.findViewById(R.id.topsheetContainer))
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogView)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.TOP

        // Set desired margin from the top (in pixels)
        val marginTopInPixels = resources.getDimensionPixelSize(R.dimen.dp_50)
        layoutParams.y = marginTopInPixels

        dialog.window?.attributes = layoutParams
        dialog.show()

        val btnRecoveryPayment = dialogView?.findViewById<Button>(R.id.btnRecoveryPayment)
        val etRecoveryName = dialogView?.findViewById<EditText>(R.id.etRecoveryName)
        val etRecoveryAmount = dialogView?.findViewById<EditText>(R.id.etRecoveryAmount)
        btnRecoveryPayment?.setOnClickListener {
            MainScope().launch{
                purchaseSharedViewModel.addTransaction(
                    TransactionEntity(
                        billNo = null,
                        Constants.LENDOUT,
                        etRecoveryName?.text.toString(),
                        "", "","",
                        paidAmt = null,
                        0,
                        etRecoveryAmount?.text.toString().toLong(),
                        partyContactNumber = null,
                        partyBillingAddress = null
                    )
                )
            }
            dialog.dismiss()
        }
    }

    private fun showCustomDialog() {
        val dialogView = layoutInflater.inflate(R.layout.top_sheet, activity?.findViewById(R.id.topsheetContainer))
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogView)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window?.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        layoutParams.gravity = Gravity.TOP

        // Set desired margin from the top (in pixels)
        val marginTopInPixels = resources.getDimensionPixelSize(R.dimen.dp_50)
        layoutParams.y = marginTopInPixels

        dialog.window?.attributes = layoutParams
        dialog.show()

        val btnGivePayment = dialogView?.findViewById<Button>(R.id.btnGivePayment)
        val etLendName = dialogView?.findViewById<EditText>(R.id.etLendName)
        val etLendAmount = dialogView?.findViewById<EditText>(R.id.etLendAmount)
        btnGivePayment?.setOnClickListener {
                    MainScope().launch{
                        purchaseSharedViewModel.addTransaction(
                            TransactionEntity(
                                billNo = null,
                                Constants.LENDIN,
                                etLendName?.text.toString(),
                                "", "","",
                                paidAmt = null,
                                0,
                                etLendAmount?.text.toString().toLong(),
                                partyContactNumber = null,
                                partyBillingAddress = null
                            )
                        )
                    }
            dialog.dismiss()
        }

    }


    private fun navigateToSaleFragment() {
        val action = ItemsFragmentDirections.actionNavItemsToNavSale()
        findNavController().navigate(action)
    }

    private fun navigateToPurchaseFragment() {
        val action = ItemsFragmentDirections.actionNavItemsToNavPurchase()
        findNavController().navigate(action)
    }

}


@Composable
fun Selector(selected: Int, onClick: (Int) -> Unit) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        SelectorCards(selected == 0, stringResource(R.string.parties_compose), 0, onClick = onClick)
        SelectorCards(selected == 1, stringResource(R.string.trans_compose), 1, onClick = onClick)
        SelectorCards(selected == 2, stringResource(R.string.items_compose), 2, onClick = onClick)
    }

}

@Composable
fun SelectorCards(isSelected: Boolean, name: String, nameCode: Int, onClick: (Int) -> Unit) {
    Row {
        Card(
            modifier = Modifier
                .width(110.dp)
                .height(35.dp)
                .clickable { onClick(nameCode) },
            shape = RoundedCornerShape(17.dp),
            border = BorderStroke(
                1.3.dp,
                color = if (isSelected) Color(0xFFCE2848) else Color.LightGray
            ),
            backgroundColor = if (isSelected) Color(0xFFF9DCE1) else Color.White
        ) {

            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    color = if (isSelected) Color(0xFFCE2848) else Color.LightGray,
                    fontSize = 12.sp,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}


@Composable
fun TotalInformationCard(name: String, total: Long, image: Int, type: Int, onClick: (Int) -> Unit) {

    Card(
        modifier = Modifier
            .height(80.dp)
            .clickable { onClick(type) },
        shape = RoundedCornerShape(5.dp),
        elevation = 10.dp
    ) {

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center

        ) {

            Row {
                Spacer(modifier = Modifier.width(15.dp))
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = image),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(3.dp))
                Text(
                    text = name,
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.width(40.dp))
            }
            Spacer(modifier = Modifier.size(10.dp))
            Row() {
                Spacer(modifier = Modifier.width(38.dp))
                Text(
                    text = "₹ $total",
                    fontFamily = robotoFamily,
                    fontWeight = FontWeight.Medium,
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.width(20.dp))
            }

        }

    }

}

@Composable
fun BottomButtons(
    onClickPurchase: () -> Unit,
    onClickMiddle: () -> Unit,
    onClickSale: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly

    ) {
        SalePurchaseButton(Color(0xFF0174E7), stringResource(R.string.pur_comp), onClickPurchase)
        CircleAddButton(onClick = onClickMiddle)
        SalePurchaseButton(Color(0xFFED1A3B), stringResource(R.string.add_sale_compose), onClickSale)
    }
}