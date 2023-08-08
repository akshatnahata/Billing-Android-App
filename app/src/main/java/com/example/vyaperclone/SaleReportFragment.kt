package com.example.vyaperclone

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vyaperclone.databinding.FragmentMenuItemsBinding
import com.example.vyaperclone.databinding.FragmentSaleReportBinding
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

@AndroidEntryPoint
class SaleReportFragment : Fragment() {

    lateinit var binding: FragmentSaleReportBinding

    var transactions = mutableListOf<TransactionEntity>()
    private val salesReportViewModel: SaleReportViewModel by viewModels()
    val adapter = SalesReportAdapter(transactions)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sale_report, container, false)
    }

    companion object {

        fun newInstance() = SaleReportFragment()

    }

    override fun onResume() {
        super.onResume()
        salesReportViewModel.getReport().observe(this, Observer {
            transactions.clear()
            transactions.addAll(it)
            adapter.notifyDataSetChanged()
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.salesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.salesRecyclerView.adapter = adapter

        binding.ibExportPdf.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                savePdf()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun savePdf() {
        val mDoc = com.itextpdf.text.Document()
        val mFileName = SimpleDateFormat("yyyyMMdd_HHmmss").format(System.currentTimeMillis())
        val mFilePath = Environment.getExternalStorageDirectory().toString() + "/" + mFileName + ".pdf"
        try{
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            mDoc.open()
            val data = "this data will enter"
            mDoc.addAuthor("Vyapar_Clone")
            val table = PdfPTable(transactions.size)
            for (i in 0..transactions.size){
                val para = Paragraph(transactions[i].toString())
                val cell = PdfPCell(para)
                table.addCell(cell)
            }
            mDoc.add(table)
            mDoc.close()
            val pdfFile = File(mFilePath, mFileName)

            if (pdfFile.exists())
            {
                val path: Uri = Uri.fromFile(pdfFile)
                val objIntent = Intent(Intent.ACTION_VIEW)
                objIntent.setDataAndType(path, "application/pdf")
                objIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(objIntent) //Starting the pdf viewer
            } else {
                Toast.makeText(activity, "The file not exists! ", Toast.LENGTH_SHORT).show()
            }
        }catch (e: Exception){}
    }

}