package com.udacoding.pdfviewer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.graphics.Canvas
import android.graphics.Color
import android.widget.Toast
import com.github.barteksc.pdfviewer.listener.OnDrawListener
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener
import com.github.barteksc.pdfviewer.listener.OnRenderListener
import com.krishna.fileloader.FileLoader
import com.krishna.fileloader.listener.FileRequestListener
import com.krishna.fileloader.pojo.FileResponse
import com.krishna.fileloader.request.FileLoadRequest
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Dexter.withActivity(this)
            .withPermissions(
                READ_EXTERNAL_STORAGE,
                WRITE_EXTERNAL_STORAGE
            )
            .withListener(object : BaseMultiplePermissionsListener(){

            }).check()


        FileLoader.with(this)
            .load("http://www.africau.edu/images/default/sample.pdf",false)
            .fromDirectory("PDFFiles", FileLoader.DIR_INTERNAL)
            .asFile(object : FileRequestListener<File> {
                override fun onLoad(request: FileLoadRequest?, response: FileResponse<File>?) {
                    val filePdf : File?  = response?.body
                    pdfView.fromFile(filePdf)
                        .password(null)
                        .defaultPage(0)
                        .enableSwipe(true)
                        .swipeHorizontal(false)
                        .enableDoubletap(true)
                        .onDrawAll { canvas, pageWidth, pageHeight, displayedPage ->

                        }
                        .onDraw { canvas, pageWidth, pageHeight, displayedPage ->

                        }
                        .onPageError { page, t ->
                            Toast.makeText(applicationContext,"Gagal Load",Toast.LENGTH_SHORT).show()
                        }
                        .onPageChange { page, pageCount ->

                        }
                        .onRender { nbPages, pageWidth, pageHeight ->
                            pdfView.fitToWidth()
                        }
                        .enableAnnotationRendering(true)
                        .invalidPageColor(Color.WHITE)
                        .load()
                }

                override fun onError(request: FileLoadRequest?, t: Throwable?) {
                    Toast.makeText(applicationContext,"Load Gagal",Toast.LENGTH_LONG).show()
                }

            })
    }
}
