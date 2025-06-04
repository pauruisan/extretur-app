package com.uax.extretur.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.uax.extretur.R
import com.uax.extretur.databinding.ActivityProfileBinding
import kotlin.toString

class ProfileActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        val prefs = getSharedPreferences("perfil", MODE_PRIVATE)
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val uriString = prefs.getString("imageUri_$uid", null)
        if (uriString != null) {
            val imageUri = Uri.parse(uriString)
            Glide.with(this)
                .load(imageUri)
                .circleCrop()
                .into(binding.imgProfile)}
        acciones()
        userData()
        navBar()


    }

    private fun acciones() {
        binding.btnLogout.setOnClickListener(this)
        binding.imgProfile.setOnClickListener(this)
        binding.txtAboutApp.setOnClickListener(this)
        binding.txtAboutAppArrow.setOnClickListener(this)
        binding.txtTerms.setOnClickListener(this)
        binding.txtTermsArrow.setOnClickListener(this)
        binding.txtPrivacy.setOnClickListener(this)
        binding.txtPrivacyArrow.setOnClickListener(this)

    }

    private fun userData (){
        val user = auth.currentUser
        val uid = user?.uid

        if (uid != null){
            val db = FirebaseDatabase.getInstance()
            val ref = db.getReference("usuarios").child(uid)

            ref.get().addOnSuccessListener { snapshot ->

                if (snapshot.exists()) {
                    val nombre = snapshot.child("nombre").value.toString()
                    val apellidos = snapshot.child("apellidos").value.toString()
                    val email = snapshot.child("email").value.toString()

                    binding.txtNombrePerfil.text = "$nombre $apellidos"
                    binding.txtCorreoPerfil.text = "$email"
                } else {
                    binding.txtNombrePerfil.text = "No encontrado"
                    binding.txtCorreoPerfil.text = "No encontrado"
                }
            }.addOnFailureListener {
                binding.txtNombrePerfil.text = "Error"
                binding.txtCorreoPerfil.text = "Error"
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            if (imageUri !=null){
                val prefs = getSharedPreferences("perfil", MODE_PRIVATE)
                val uid = FirebaseAuth.getInstance().currentUser?.uid?: return
                prefs.edit().putString("imageUri_$uid", imageUri.toString()).apply()

                Glide.with(this)
                    .load(imageUri)
                    .circleCrop()
                    .into(binding.imgProfile)
            }

        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.btnLogout.id -> {
                auth = FirebaseAuth.getInstance()
                auth.signOut()
                Toast.makeText(this, "Sesión cerrada correctamente", Toast.LENGTH_SHORT).show()
                val intent = Intent(applicationContext, LogInActivity::class.java)
                startActivity(intent)
                finish()
            }
            binding.imgProfile.id -> {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 100)
            }
            binding.txtAboutApp.id  -> {
                showDialog("Sobre la aplicación", "Esta app muestra información turística de Extremadura...")
            }
            binding.txtAboutAppArrow.id -> {
                showDialog("Sobre la aplicación", "Esta app muestra información turística de Extremadura...")
            }
            binding.txtTerms.id -> {
                showDialog("Términos y condiciones", "Last updated: May 23, 2025\n" +
                        "\n" +
                        "Please read these terms and conditions carefully before using Our Service.\n" +
                        "\n" +
                        "Interpretation and Definitions  \n" +
                        "------------------------------\n" +
                        "\n" +
                        "Interpretation  \n" +
                        "~~~~~~~~~~~~~~\n" +
                        "\n" +
                        "The words of which the initial letter is capitalized have meanings defined\n" +
                        "under the following conditions. The following definitions shall have the same\n" +
                        "meaning regardless of whether they appear in singular or in plural.\n" +
                        "\n" +
                        "Definitions  \n" +
                        "~~~~~~~~~~~\n" +
                        "\n" +
                        "For the purposes of these Terms and Conditions:\n" +
                        "\n" +
                        "  * Application means the software program provided by the Company downloaded\n" +
                        "    by You on any electronic device, named ExtreTur\n" +
                        "\n" +
                        "  * Application Store means the digital distribution service operated and\n" +
                        "    developed by Apple Inc. (Apple App Store) or Google Inc. (Google Play\n" +
                        "    Store) in which the Application has been downloaded.\n" +
                        "\n" +
                        "  * Affiliate means an entity that controls, is controlled by or is under\n" +
                        "    common control with a party, where \"control\" means ownership of 50% or\n" +
                        "    more of the shares, equity interest or other securities entitled to vote\n" +
                        "    for election of directors or other managing authority.\n" +
                        "\n" +
                        "  * Country refers to: Spain\n" +
                        "\n" +
                        "  * Company (referred to as either \"the Company\", \"We\", \"Us\" or \"Our\" in this\n" +
                        "    Agreement) refers to ExtreTur.\n" +
                        "\n" +
                        "  * Device means any device that can access the Service such as a computer, a\n" +
                        "    cellphone or a digital tablet.\n" +
                        "\n" +
                        "  * Service refers to the Application.\n" +
                        "\n" +
                        "  * Terms and Conditions (also referred as \"Terms\") mean these Terms and\n" +
                        "    Conditions that form the entire agreement between You and the Company\n" +
                        "    regarding the use of the Service. This Terms and Conditions agreement has\n" +
                        "    been created with the help of the [Free Terms and Conditions\n" +
                        "    Generator](https://www.freeprivacypolicy.com/free-terms-and-conditions-\n" +
                        "    generator/).\n" +
                        "\n" +
                        "  * Third-party Social Media Service means any services or content (including\n" +
                        "    data, information, products or services) provided by a third-party that\n" +
                        "    may be displayed, included or made available by the Service.\n" +
                        "\n" +
                        "  * You means the individual accessing or using the Service, or the company,\n" +
                        "    or other legal entity on behalf of which such individual is accessing or\n" +
                        "    using the Service, as applicable.\n" +
                        "\n" +
                        "\n" +
                        "Acknowledgment  \n" +
                        "--------------\n" +
                        "\n" +
                        "These are the Terms and Conditions governing the use of this Service and the\n" +
                        "agreement that operates between You and the Company. These Terms and\n" +
                        "Conditions set out the rights and obligations of all users regarding the use\n" +
                        "of the Service.\n" +
                        "\n" +
                        "Your access to and use of the Service is conditioned on Your acceptance of and\n" +
                        "compliance with these Terms and Conditions. These Terms and Conditions apply\n" +
                        "to all visitors, users and others who access or use the Service.\n" +
                        "\n" +
                        "By accessing or using the Service You agree to be bound by these Terms and\n" +
                        "Conditions. If You disagree with any part of these Terms and Conditions then\n" +
                        "You may not access the Service.\n" +
                        "\n" +
                        "You represent that you are over the age of 18. The Company does not permit\n" +
                        "those under 18 to use the Service.\n" +
                        "\n" +
                        "Your access to and use of the Service is also conditioned on Your acceptance\n" +
                        "of and compliance with the Privacy Policy of the Company. Our Privacy Policy\n" +
                        "describes Our policies and procedures on the collection, use and disclosure of\n" +
                        "Your personal information when You use the Application or the Website and\n" +
                        "tells You about Your privacy rights and how the law protects You. Please read\n" +
                        "Our Privacy Policy carefully before using Our Service.\n" +
                        "\n" +
                        "Links to Other Websites  \n" +
                        "-----------------------\n" +
                        "\n" +
                        "Our Service may contain links to third-party web sites or services that are\n" +
                        "not owned or controlled by the Company.\n" +
                        "\n" +
                        "The Company has no control over, and assumes no responsibility for, the\n" +
                        "content, privacy policies, or practices of any third party web sites or\n" +
                        "services. You further acknowledge and agree that the Company shall not be\n" +
                        "responsible or liable, directly or indirectly, for any damage or loss caused\n" +
                        "or alleged to be caused by or in connection with the use of or reliance on any\n" +
                        "such content, goods or services available on or through any such web sites or\n" +
                        "services.\n" +
                        "\n" +
                        "We strongly advise You to read the terms and conditions and privacy policies\n" +
                        "of any third-party web sites or services that You visit.\n" +
                        "\n" +
                        "Termination  \n" +
                        "-----------\n" +
                        "\n" +
                        "We may terminate or suspend Your access immediately, without prior notice or\n" +
                        "liability, for any reason whatsoever, including without limitation if You\n" +
                        "breach these Terms and Conditions.\n" +
                        "\n" +
                        "Upon termination, Your right to use the Service will cease immediately.\n" +
                        "\n" +
                        "Limitation of Liability  \n" +
                        "-----------------------\n" +
                        "\n" +
                        "Notwithstanding any damages that You might incur, the entire liability of the\n" +
                        "Company and any of its suppliers under any provision of this Terms and Your\n" +
                        "exclusive remedy for all of the foregoing shall be limited to the amount\n" +
                        "actually paid by You through the Service or 100 USD if You haven't purchased\n" +
                        "anything through the Service.\n" +
                        "\n" +
                        "To the maximum extent permitted by applicable law, in no event shall the\n" +
                        "Company or its suppliers be liable for any special, incidental, indirect, or\n" +
                        "consequential damages whatsoever (including, but not limited to, damages for\n" +
                        "loss of profits, loss of data or other information, for business interruption,\n" +
                        "for personal injury, loss of privacy arising out of or in any way related to\n" +
                        "the use of or inability to use the Service, third-party software and/or third-\n" +
                        "party hardware used with the Service, or otherwise in connection with any\n" +
                        "provision of this Terms), even if the Company or any supplier has been advised\n" +
                        "of the possibility of such damages and even if the remedy fails of its\n" +
                        "essential purpose.\n" +
                        "\n" +
                        "Some states do not allow the exclusion of implied warranties or limitation of\n" +
                        "liability for incidental or consequential damages, which means that some of\n" +
                        "the above limitations may not apply. In these states, each party's liability\n" +
                        "will be limited to the greatest extent permitted by law.\n" +
                        "\n" +
                        "\"AS IS\" and \"AS AVAILABLE\" Disclaimer  \n" +
                        "----------\n" +
                        "\n" +
                        "The Service is provided to You \"AS IS\" and \"AS AVAILABLE\" and with all faults\n" +
                        "and defects without warranty of any kind. To the maximum extent permitted\n" +
                        "under applicable law, the Company, on its own behalf and on behalf of its\n" +
                        "Affiliates and its and their respective licensors and service providers,\n" +
                        "expressly disclaims all warranties, whether express, implied, statutory or\n" +
                        "otherwise, with respect to the Service, including all implied warranties of\n" +
                        "merchantability, fitness for a particular purpose, title and non-infringement,\n" +
                        "and warranties that may arise out of course of dealing, course of performance,\n" +
                        "usage or trade practice. Without limitation to the foregoing, the Company\n" +
                        "provides no warranty or undertaking, and makes no representation of any kind\n" +
                        "that the Service will meet Your requirements, achieve any intended results, be\n" +
                        "compatible or work with any other software, applications, systems or services,\n" +
                        "operate without interruption, meet any performance or reliability standards or\n" +
                        "be error free or that any errors or defects can or will be corrected.\n" +
                        "\n" +
                        "Without limiting the foregoing, neither the Company nor any of the company's\n" +
                        "provider makes any representation or warranty of any kind, express or implied:\n" +
                        "(i) as to the operation or availability of the Service, or the information,\n" +
                        "content, and materials or products included thereon; (ii) that the Service\n" +
                        "will be uninterrupted or error-free; (iii) as to the accuracy, reliability, or\n" +
                        "currency of any information or content provided through the Service; or (iv)\n" +
                        "that the Service, its servers, the content, or e-mails sent from or on behalf\n" +
                        "of the Company are free of viruses, scripts, trojan horses, worms, malware,\n" +
                        "timebombs or other harmful components.\n" +
                        "\n" +
                        "Some jurisdictions do not allow the exclusion of certain types of warranties\n" +
                        "or limitations on applicable statutory rights of a consumer, so some or all of\n" +
                        "the above exclusions and limitations may not apply to You. But in such a case\n" +
                        "the exclusions and limitations set forth in this section shall be applied to\n" +
                        "the greatest extent enforceable under applicable law.\n" +
                        "\n" +
                        "Governing Law  \n" +
                        "-------------\n" +
                        "\n" +
                        "The laws of the Country, excluding its conflicts of law rules, shall govern\n" +
                        "this Terms and Your use of the Service. Your use of the Application may also\n" +
                        "be subject to other local, state, national, or international laws.\n" +
                        "\n" +
                        "Disputes Resolution  \n" +
                        "-------------------\n" +
                        "\n" +
                        "If You have any concern or dispute about the Service, You agree to first try\n" +
                        "to resolve the dispute informally by contacting the Company.\n" +
                        "\n" +
                        "For European Union (EU) Users  \n" +
                        "-----------------------------\n" +
                        "\n" +
                        "If You are a European Union consumer, you will benefit from any mandatory\n" +
                        "provisions of the law of the country in which You are resident.\n" +
                        "\n" +
                        "United States Legal Compliance  \n" +
                        "------------------------------\n" +
                        "\n" +
                        "You represent and warrant that (i) You are not located in a country that is\n" +
                        "subject to the United States government embargo, or that has been designated\n" +
                        "by the United States government as a \"terrorist supporting\" country, and (ii)\n" +
                        "You are not listed on any United States government list of prohibited or\n" +
                        "restricted parties.\n" +
                        "\n" +
                        "Severability and Waiver  \n" +
                        "-----------------------\n" +
                        "\n" +
                        "Severability  \n" +
                        "~~~~~~~~~~~~\n" +
                        "\n" +
                        "If any provision of these Terms is held to be unenforceable or invalid, such\n" +
                        "provision will be changed and interpreted to accomplish the objectives of such\n" +
                        "provision to the greatest extent possible under applicable law and the\n" +
                        "remaining provisions will continue in full force and effect.\n" +
                        "\n" +
                        "Waiver  \n" +
                        "~~~~~~\n" +
                        "\n" +
                        "Except as provided herein, the failure to exercise a right or to require\n" +
                        "performance of an obligation under these Terms shall not affect a party's\n" +
                        "ability to exercise such right or require such performance at any time\n" +
                        "thereafter nor shall the waiver of a breach constitute a waiver of any\n" +
                        "subsequent breach.\n" +
                        "\n" +
                        "Translation Interpretation  \n" +
                        "--------------------------\n" +
                        "\n" +
                        "These Terms and Conditions may have been translated if We have made them\n" +
                        "available to You on our Service. You agree that the original English text\n" +
                        "shall prevail in the case of a dispute.\n" +
                        "\n" +
                        "Changes to These Terms and Conditions  \n" +
                        "-------------------------------------\n" +
                        "\n" +
                        "We reserve the right, at Our sole discretion, to modify or replace these Terms\n" +
                        "at any time. If a revision is material We will make reasonable efforts to\n" +
                        "provide at least 30 days' notice prior to any new terms taking effect. What\n" +
                        "constitutes a material change will be determined at Our sole discretion.\n" +
                        "\n" +
                        "By continuing to access or use Our Service after those revisions become\n" +
                        "effective, You agree to be bound by the revised terms. If You do not agree to\n" +
                        "the new terms, in whole or in part, please stop using the website and the\n" +
                        "Service.\n")
            }
            binding.txtTermsArrow.id -> {
                showDialog("Términos y condiciones", "Last updated: May 23, 2025\n" +
                        "\n" +
                        "Please read these terms and conditions carefully before using Our Service.\n" +
                        "\n" +
                        "Interpretation and Definitions  \n" +
                        "------------------------------\n" +
                        "\n" +
                        "Interpretation  \n" +
                        "~~~~~~~~~~~~~~\n" +
                        "\n" +
                        "The words of which the initial letter is capitalized have meanings defined\n" +
                        "under the following conditions. The following definitions shall have the same\n" +
                        "meaning regardless of whether they appear in singular or in plural.\n" +
                        "\n" +
                        "Definitions  \n" +
                        "~~~~~~~~~~~\n" +
                        "\n" +
                        "For the purposes of these Terms and Conditions:\n" +
                        "\n" +
                        "  * Application means the software program provided by the Company downloaded\n" +
                        "    by You on any electronic device, named ExtreTur\n" +
                        "\n" +
                        "  * Application Store means the digital distribution service operated and\n" +
                        "    developed by Apple Inc. (Apple App Store) or Google Inc. (Google Play\n" +
                        "    Store) in which the Application has been downloaded.\n" +
                        "\n" +
                        "  * Affiliate means an entity that controls, is controlled by or is under\n" +
                        "    common control with a party, where \"control\" means ownership of 50% or\n" +
                        "    more of the shares, equity interest or other securities entitled to vote\n" +
                        "    for election of directors or other managing authority.\n" +
                        "\n" +
                        "  * Country refers to: Spain\n" +
                        "\n" +
                        "  * Company (referred to as either \"the Company\", \"We\", \"Us\" or \"Our\" in this\n" +
                        "    Agreement) refers to ExtreTur.\n" +
                        "\n" +
                        "  * Device means any device that can access the Service such as a computer, a\n" +
                        "    cellphone or a digital tablet.\n" +
                        "\n" +
                        "  * Service refers to the Application.\n" +
                        "\n" +
                        "  * Terms and Conditions (also referred as \"Terms\") mean these Terms and\n" +
                        "    Conditions that form the entire agreement between You and the Company\n" +
                        "    regarding the use of the Service. This Terms and Conditions agreement has\n" +
                        "    been created with the help of the [Free Terms and Conditions\n" +
                        "    Generator](https://www.freeprivacypolicy.com/free-terms-and-conditions-\n" +
                        "    generator/).\n" +
                        "\n" +
                        "  * Third-party Social Media Service means any services or content (including\n" +
                        "    data, information, products or services) provided by a third-party that\n" +
                        "    may be displayed, included or made available by the Service.\n" +
                        "\n" +
                        "  * You means the individual accessing or using the Service, or the company,\n" +
                        "    or other legal entity on behalf of which such individual is accessing or\n" +
                        "    using the Service, as applicable.\n" +
                        "\n" +
                        "\n" +
                        "Acknowledgment  \n" +
                        "--------------\n" +
                        "\n" +
                        "These are the Terms and Conditions governing the use of this Service and the\n" +
                        "agreement that operates between You and the Company. These Terms and\n" +
                        "Conditions set out the rights and obligations of all users regarding the use\n" +
                        "of the Service.\n" +
                        "\n" +
                        "Your access to and use of the Service is conditioned on Your acceptance of and\n" +
                        "compliance with these Terms and Conditions. These Terms and Conditions apply\n" +
                        "to all visitors, users and others who access or use the Service.\n" +
                        "\n" +
                        "By accessing or using the Service You agree to be bound by these Terms and\n" +
                        "Conditions. If You disagree with any part of these Terms and Conditions then\n" +
                        "You may not access the Service.\n" +
                        "\n" +
                        "You represent that you are over the age of 18. The Company does not permit\n" +
                        "those under 18 to use the Service.\n" +
                        "\n" +
                        "Your access to and use of the Service is also conditioned on Your acceptance\n" +
                        "of and compliance with the Privacy Policy of the Company. Our Privacy Policy\n" +
                        "describes Our policies and procedures on the collection, use and disclosure of\n" +
                        "Your personal information when You use the Application or the Website and\n" +
                        "tells You about Your privacy rights and how the law protects You. Please read\n" +
                        "Our Privacy Policy carefully before using Our Service.\n" +
                        "\n" +
                        "Links to Other Websites  \n" +
                        "-----------------------\n" +
                        "\n" +
                        "Our Service may contain links to third-party web sites or services that are\n" +
                        "not owned or controlled by the Company.\n" +
                        "\n" +
                        "The Company has no control over, and assumes no responsibility for, the\n" +
                        "content, privacy policies, or practices of any third party web sites or\n" +
                        "services. You further acknowledge and agree that the Company shall not be\n" +
                        "responsible or liable, directly or indirectly, for any damage or loss caused\n" +
                        "or alleged to be caused by or in connection with the use of or reliance on any\n" +
                        "such content, goods or services available on or through any such web sites or\n" +
                        "services.\n" +
                        "\n" +
                        "We strongly advise You to read the terms and conditions and privacy policies\n" +
                        "of any third-party web sites or services that You visit.\n" +
                        "\n" +
                        "Termination  \n" +
                        "-----------\n" +
                        "\n" +
                        "We may terminate or suspend Your access immediately, without prior notice or\n" +
                        "liability, for any reason whatsoever, including without limitation if You\n" +
                        "breach these Terms and Conditions.\n" +
                        "\n" +
                        "Upon termination, Your right to use the Service will cease immediately.\n" +
                        "\n" +
                        "Limitation of Liability  \n" +
                        "-----------------------\n" +
                        "\n" +
                        "Notwithstanding any damages that You might incur, the entire liability of the\n" +
                        "Company and any of its suppliers under any provision of this Terms and Your\n" +
                        "exclusive remedy for all of the foregoing shall be limited to the amount\n" +
                        "actually paid by You through the Service or 100 USD if You haven't purchased\n" +
                        "anything through the Service.\n" +
                        "\n" +
                        "To the maximum extent permitted by applicable law, in no event shall the\n" +
                        "Company or its suppliers be liable for any special, incidental, indirect, or\n" +
                        "consequential damages whatsoever (including, but not limited to, damages for\n" +
                        "loss of profits, loss of data or other information, for business interruption,\n" +
                        "for personal injury, loss of privacy arising out of or in any way related to\n" +
                        "the use of or inability to use the Service, third-party software and/or third-\n" +
                        "party hardware used with the Service, or otherwise in connection with any\n" +
                        "provision of this Terms), even if the Company or any supplier has been advised\n" +
                        "of the possibility of such damages and even if the remedy fails of its\n" +
                        "essential purpose.\n" +
                        "\n" +
                        "Some states do not allow the exclusion of implied warranties or limitation of\n" +
                        "liability for incidental or consequential damages, which means that some of\n" +
                        "the above limitations may not apply. In these states, each party's liability\n" +
                        "will be limited to the greatest extent permitted by law.\n" +
                        "\n" +
                        "\"AS IS\" and \"AS AVAILABLE\" Disclaimer  \n" +
                        "----------\n" +
                        "\n" +
                        "The Service is provided to You \"AS IS\" and \"AS AVAILABLE\" and with all faults\n" +
                        "and defects without warranty of any kind. To the maximum extent permitted\n" +
                        "under applicable law, the Company, on its own behalf and on behalf of its\n" +
                        "Affiliates and its and their respective licensors and service providers,\n" +
                        "expressly disclaims all warranties, whether express, implied, statutory or\n" +
                        "otherwise, with respect to the Service, including all implied warranties of\n" +
                        "merchantability, fitness for a particular purpose, title and non-infringement,\n" +
                        "and warranties that may arise out of course of dealing, course of performance,\n" +
                        "usage or trade practice. Without limitation to the foregoing, the Company\n" +
                        "provides no warranty or undertaking, and makes no representation of any kind\n" +
                        "that the Service will meet Your requirements, achieve any intended results, be\n" +
                        "compatible or work with any other software, applications, systems or services,\n" +
                        "operate without interruption, meet any performance or reliability standards or\n" +
                        "be error free or that any errors or defects can or will be corrected.\n" +
                        "\n" +
                        "Without limiting the foregoing, neither the Company nor any of the company's\n" +
                        "provider makes any representation or warranty of any kind, express or implied:\n" +
                        "(i) as to the operation or availability of the Service, or the information,\n" +
                        "content, and materials or products included thereon; (ii) that the Service\n" +
                        "will be uninterrupted or error-free; (iii) as to the accuracy, reliability, or\n" +
                        "currency of any information or content provided through the Service; or (iv)\n" +
                        "that the Service, its servers, the content, or e-mails sent from or on behalf\n" +
                        "of the Company are free of viruses, scripts, trojan horses, worms, malware,\n" +
                        "timebombs or other harmful components.\n" +
                        "\n" +
                        "Some jurisdictions do not allow the exclusion of certain types of warranties\n" +
                        "or limitations on applicable statutory rights of a consumer, so some or all of\n" +
                        "the above exclusions and limitations may not apply to You. But in such a case\n" +
                        "the exclusions and limitations set forth in this section shall be applied to\n" +
                        "the greatest extent enforceable under applicable law.\n" +
                        "\n" +
                        "Governing Law  \n" +
                        "-------------\n" +
                        "\n" +
                        "The laws of the Country, excluding its conflicts of law rules, shall govern\n" +
                        "this Terms and Your use of the Service. Your use of the Application may also\n" +
                        "be subject to other local, state, national, or international laws.\n" +
                        "\n" +
                        "Disputes Resolution  \n" +
                        "-------------------\n" +
                        "\n" +
                        "If You have any concern or dispute about the Service, You agree to first try\n" +
                        "to resolve the dispute informally by contacting the Company.\n" +
                        "\n" +
                        "For European Union (EU) Users  \n" +
                        "-----------------------------\n" +
                        "\n" +
                        "If You are a European Union consumer, you will benefit from any mandatory\n" +
                        "provisions of the law of the country in which You are resident.\n" +
                        "\n" +
                        "United States Legal Compliance  \n" +
                        "------------------------------\n" +
                        "\n" +
                        "You represent and warrant that (i) You are not located in a country that is\n" +
                        "subject to the United States government embargo, or that has been designated\n" +
                        "by the United States government as a \"terrorist supporting\" country, and (ii)\n" +
                        "You are not listed on any United States government list of prohibited or\n" +
                        "restricted parties.\n" +
                        "\n" +
                        "Severability and Waiver  \n" +
                        "-----------------------\n" +
                        "\n" +
                        "Severability  \n" +
                        "~~~~~~~~~~~~\n" +
                        "\n" +
                        "If any provision of these Terms is held to be unenforceable or invalid, such\n" +
                        "provision will be changed and interpreted to accomplish the objectives of such\n" +
                        "provision to the greatest extent possible under applicable law and the\n" +
                        "remaining provisions will continue in full force and effect.\n" +
                        "\n" +
                        "Waiver  \n" +
                        "~~~~~~\n" +
                        "\n" +
                        "Except as provided herein, the failure to exercise a right or to require\n" +
                        "performance of an obligation under these Terms shall not affect a party's\n" +
                        "ability to exercise such right or require such performance at any time\n" +
                        "thereafter nor shall the waiver of a breach constitute a waiver of any\n" +
                        "subsequent breach.\n" +
                        "\n" +
                        "Translation Interpretation  \n" +
                        "--------------------------\n" +
                        "\n" +
                        "These Terms and Conditions may have been translated if We have made them\n" +
                        "available to You on our Service. You agree that the original English text\n" +
                        "shall prevail in the case of a dispute.\n" +
                        "\n" +
                        "Changes to These Terms and Conditions  \n" +
                        "-------------------------------------\n" +
                        "\n" +
                        "We reserve the right, at Our sole discretion, to modify or replace these Terms\n" +
                        "at any time. If a revision is material We will make reasonable efforts to\n" +
                        "provide at least 30 days' notice prior to any new terms taking effect. What\n" +
                        "constitutes a material change will be determined at Our sole discretion.\n" +
                        "\n" +
                        "By continuing to access or use Our Service after those revisions become\n" +
                        "effective, You agree to be bound by the revised terms. If You do not agree to\n" +
                        "the new terms, in whole or in part, please stop using the website and the\n" +
                        "Service.\n")
            }
            binding.txtPrivacy.id -> {
                showDialog("Política de privacidad", "Last updated: May 23, 2025\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"This Privacy Policy describes Our policies and procedures on the collection,\\n\" +\n" +
                        "                        \"use and disclosure of Your information when You use the Service and tells You\\n\" +\n" +
                        "                        \"about Your privacy rights and how the law protects You.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"We use Your Personal data to provide and improve the Service. By using the\\n\" +\n" +
                        "                        \"Service, You agree to the collection and use of information in accordance with\\n\" +\n" +
                        "                        \"this Privacy Policy. This Privacy Policy has been created with the help of the\\n\" +\n" +
                        "                        \"[Free Privacy Policy Generator](https://www.freeprivacypolicy.com/free-\\n\" +\n" +
                        "                        \"privacy-policy-generator/).\\n\" +\n" +
                        "                        \"Interpretation and Definitions  \\n\" +\n" +
                        "                        \"------------------------------\\n\" +\n" +
                        "                        \"Interpretation  \\n\" +\n" +
                        "                        \"~~~~~~~~~~~~~~\\n\" +\n" +
                        "                        \"The words of which the initial letter is capitalized have meanings defined\\n\" +\n" +
                        "                        \"under the following conditions. The following definitions shall have the same\\n\" +\n" +
                        "                        \"meaning regardless of whether they appear in singular or in plural.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Definitions  \\n\" +\n" +
                        "                        \"~~~~~~~~~~~\\n\" +\n" +
                        "                        \"For the purposes of this Privacy Policy:\\n\" +\n" +
                        "                        \"  * Account means a unique account created for You to access our Service or\\n\" +\n" +
                        "                        \"    parts of our Service.\\n\" +\n" +
                        "                        \"  * Affiliate means an entity that controls, is controlled by or is under\\n\" +\n" +
                        "                        \"    common control with a party, where \\\"control\\\" means ownership of 50% or\\n\" +\n" +
                        "                        \"    more of the shares, equity interest or other securities entitled to vote\\n\" +\n" +
                        "                        \"    for election of directors or other managing authority.\\n\" +\n" +
                        "                        \"  * Application refers to ExtreTur, the software program provided by the\\n\" +\n" +
                        "                        \"    Company.\\n\" +\n" +
                        "                        \"  * Company (referred to as either \\\"the Company\\\", \\\"We\\\", \\\"Us\\\" or \\\"Our\\\" in this\\n\" +\n" +
                        "                        \"    Agreement) refers to ExtreTur.\\n\" +\n" +
                        "                        \"  * Country refers to: Spain\\n\" +\n" +
                        "                        \"  * Device means any device that can access the Service such as a computer, a\\n\" +\n" +
                        "                        \"    cellphone or a digital tablet.\\n\" +\n" +
                        "                        \"  * Personal Data is any information that relates to an identified or\\n\" +\n" +
                        "                        \"    identifiable individual.\\n\" +\n" +
                        "                        \"  * Service refers to the Application.\\n\" +\n" +
                        "                        \"  * Service Provider means any natural or legal person who processes the data\\n\" +\n" +
                        "                        \"    on behalf of the Company. It refers to third-party companies or\\n\" +\n" +
                        "                        \"    individuals employed by the Company to facilitate the Service, to provide\\n\" +\n" +
                        "                        \"    the Service on behalf of the Company, to perform services related to the\\n\" +\n" +
                        "                        \"    Service or to assist the Company in analyzing how the Service is used.\\n\" +\n" +
                        "                        \"  * Usage Data refers to data collected automatically, either generated by the\\n\" +\n" +
                        "                        \"    use of the Service or from the Service infrastructure itself (for example,\\n\" +\n" +
                        "                        \"    the duration of a page visit).\\n\" +\n" +
                        "                        \"  * You means the individual accessing or using the Service, or the company,\\n\" +\n" +
                        "                        \"    or other legal entity on behalf of which such individual is accessing or\\n\" +\n" +
                        "                        \"    using the Service, as applicable.\\n\" +\n" +
                        "                        \"Collecting and Using Your Personal Data  \\n\" +\n" +
                        "                        \"---------------------------------------\\n\" +\n" +
                        "                        \"Types of Data Collected  \\n\" +\n" +
                        "                        \"~~~~~~~~~~~~~~~~~~~~~~~\\n\" +\n" +
                        "                        \"Personal Data  \\n\" +\n" +
                        "                        \"*************\\n\" +\n" +
                        "                        \"While using Our Service, We may ask You to provide Us with certain personally\\n\" +\n" +
                        "                        \"identifiable information that can be used to contact or identify You.\\n\" +\n" +
                        "                        \"Personally identifiable information may include, but is not limited to:\\n\" +\n" +
                        "                        \"  * Email address\\n\" +\n" +
                        "                        \"  * First name and last name\\n\" +\n" +
                        "                        \"  * Usage Data\\n\" +\n" +
                        "                        \"Usage Data  \\n\" +\n" +
                        "                        \"**********\\n\" +\n" +
                        "                        \"Usage Data is collected automatically when using the Service.\\n\" +\n" +
                        "                        \"Usage Data may include information such as Your Device's Internet Protocol\\n\" +\n" +
                        "                        \"address (e.g. IP address), browser type, browser version, the pages of our\\n\" +\n" +
                        "                        \"Service that You visit, the time and date of Your visit, the time spent on\\n\" +\n" +
                        "                        \"those pages, unique device identifiers and other diagnostic data.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"When You access the Service by or through a mobile device, We may collect\\n\" +\n" +
                        "                        \"certain information automatically, including, but not limited to, the type of\\n\" +\n" +
                        "                        \"mobile device You use, Your mobile device unique ID, the IP address of Your\\n\" +\n" +
                        "                        \"mobile device, Your mobile operating system, the type of mobile Internet\\n\" +\n" +
                        "                        \"browser You use, unique device identifiers and other diagnostic data.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"We may also collect information that Your browser sends whenever You visit our\\n\" +\n" +
                        "                        \"Service or when You access the Service by or through a mobile device.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Use of Your Personal Data  \\n\" +\n" +
                        "                        \"~~~~~~~~~~~~~~~~~~~~~~~~~\\n\" +\n" +
                        "                        \"The Company may use Personal Data for the following purposes:\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"  * To provide and maintain our Service , including to monitor the usage of\\n\" +\n" +
                        "                        \"    our Service.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"  * To manage Your Account: to manage Your registration as a user of the\\n\" +\n" +
                        "                        \"    Service. The Personal Data You provide can give You access to different\\n\" +\n" +
                        "                        \"    functionalities of the Service that are available to You as a registered\\n\" +\n" +
                        "                        \"    user.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"  * For the performance of a contract: the development, compliance and\\n\" +\n" +
                        "                        \"    undertaking of the purchase contract for the products, items or services\\n\" +\n" +
                        "                        \"    You have purchased or of any other contract with Us through the Service.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"  * To contact You: To contact You by email, telephone calls, SMS, or other\\n\" +\n" +
                        "                        \"    equivalent forms of electronic communication, such as a mobile\\n\" +\n" +
                        "                        \"    application's push notifications regarding updates or informative\\n\" +\n" +
                        "                        \"    communications related to the functionalities, products or contracted\\n\" +\n" +
                        "                        \"    services, including the security updates, when necessary or reasonable for\\n\" +\n" +
                        "                        \"    their implementation.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"  * To provide You with news, special offers and general information about\\n\" +\n" +
                        "                        \"    other goods, services and events which we offer that are similar to those\\n\" +\n" +
                        "                        \"    that you have already purchased or enquired about unless You have opted\\n\" +\n" +
                        "                        \"    not to receive such information.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"  * To manage Your requests: To attend and manage Your requests to Us.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"  * For business transfers: We may use Your information to evaluate or conduct\\n\" +\n" +
                        "                        \"    a merger, divestiture, restructuring, reorganization, dissolution, or\\n\" +\n" +
                        "                        \"    other sale or transfer of some or all of Our assets, whether as a going\\n\" +\n" +
                        "                        \"    concern or as part of bankruptcy, liquidation, or similar proceeding, in\\n\" +\n" +
                        "                        \"    which Personal Data held by Us about our Service users is among the assets\\n\" +\n" +
                        "                        \"    transferred.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"  * For other purposes : We may use Your information for other purposes, such\\n\" +\n" +
                        "                        \"    as data analysis, identifying usage trends, determining the effectiveness\\n\" +\n" +
                        "                        \"    of our promotional campaigns and to evaluate and improve our Service,\\n\" +\n" +
                        "                        \"    products, services, marketing and your experience.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"We may share Your personal information in the following situations:\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"  * With Service Providers: We may share Your personal information with\\n\" +\n" +
                        "                        \"    Service Providers to monitor and analyze the use of our Service, to\\n\" +\n" +
                        "                        \"    contact You.\\n\" +\n" +
                        "                        \"  * For business transfers: We may share or transfer Your personal information\\n\" +\n" +
                        "                        \"    in connection with, or during negotiations of, any merger, sale of Company\\n\" +\n" +
                        "                        \"    assets, financing, or acquisition of all or a portion of Our business to\\n\" +\n" +
                        "                        \"    another company.\\n\" +\n" +
                        "                        \"  * With Affiliates: We may share Your information with Our affiliates, in\\n\" +\n" +
                        "                        \"    which case we will require those affiliates to honor this Privacy Policy.\\n\" +\n" +
                        "                        \"    Affiliates include Our parent company and any other subsidiaries, joint\\n\" +\n" +
                        "                        \"    venture partners or other companies that We control or that are under\\n\" +\n" +
                        "                        \"    common control with Us.\\n\" +\n" +
                        "                        \"  * With business partners: We may share Your information with Our business\\n\" +\n" +
                        "                        \"    partners to offer You certain products, services or promotions.\\n\" +\n" +
                        "                        \"  * With other users: when You share personal information or otherwise\\n\" +\n" +
                        "                        \"    interact in the public areas with other users, such information may be\\n\" +\n" +
                        "                        \"    viewed by all users and may be publicly distributed outside.\\n\" +\n" +
                        "                        \"  * With Your consent : We may disclose Your personal information for any\\n\" +\n" +
                        "                        \"    other purpose with Your consent.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Retention of Your Personal Data  \\n\" +\n" +
                        "                        \"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"The Company will retain Your Personal Data only for as long as is necessary\\n\" +\n" +
                        "                        \"for the purposes set out in this Privacy Policy. We will retain and use Your\\n\" +\n" +
                        "                        \"Personal Data to the extent necessary to comply with our legal obligations\\n\" +\n" +
                        "                        \"(for example, if we are required to retain your data to comply with applicable\\n\" +\n" +
                        "                        \"laws), resolve disputes, and enforce our legal agreements and policies.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"The Company will also retain Usage Data for internal analysis purposes. Usage\\n\" +\n" +
                        "                        \"Data is generally retained for a shorter period of time, except when this data\\n\" +\n" +
                        "                        \"is used to strengthen the security or to improve the functionality of Our\\n\" +\n" +
                        "                        \"Service, or We are legally obligated to retain this data for longer time\\n\" +\n" +
                        "                        \"periods.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Transfer of Your Personal Data  \\n\" +\n" +
                        "                        \"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Your information, including Personal Data, is processed at the Company's\\n\" +\n" +
                        "                        \"operating offices and in any other places where the parties involved in the\\n\" +\n" +
                        "                        \"processing are located. It means that this information may be transferred to —\\n\" +\n" +
                        "                        \"and maintained on — computers located outside of Your state, province, country\\n\" +\n" +
                        "                        \"or other governmental jurisdiction where the data protection laws may differ\\n\" +\n" +
                        "                        \"than those from Your jurisdiction.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Your consent to this Privacy Policy followed by Your submission of such\\n\" +\n" +
                        "                        \"information represents Your agreement to that transfer.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"The Company will take all steps reasonably necessary to ensure that Your data\\n\" +\n" +
                        "                        \"is treated securely and in accordance with this Privacy Policy and no transfer\\n\" +\n" +
                        "                        \"of Your Personal Data will take place to an organization or a country unless\\n\" +\n" +
                        "                        \"there are adequate controls in place including the security of Your data and\\n\" +\n" +
                        "                        \"other personal information.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Delete Your Personal Data  \\n\" +\n" +
                        "                        \"~~~~~~~~~~~~~~~~~~~~~~~~~\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"You have the right to delete or request that We assist in deleting the\\n\" +\n" +
                        "                        \"Personal Data that We have collected about You.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Our Service may give You the ability to delete certain information about You\\n\" +\n" +
                        "                        \"from within the Service.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"You may update, amend, or delete Your information at any time by signing in to\\n\" +\n" +
                        "                        \"Your Account, if you have one, and visiting the account settings section that\\n\" +\n" +
                        "                        \"allows you to manage Your personal information. You may also contact Us to\\n\" +\n" +
                        "                        \"request access to, correct, or delete any personal information that You have\\n\" +\n" +
                        "                        \"provided to Us.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Please note, however, that We may need to retain certain information when we\\n\" +\n" +
                        "                        \"have a legal obligation or lawful basis to do so.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Disclosure of Your Personal Data  \\n\" +\n" +
                        "                        \"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Business Transactions  \\n\" +\n" +
                        "                        \"*********************\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"If the Company is involved in a merger, acquisition or asset sale, Your\\n\" +\n" +
                        "                        \"Personal Data may be transferred. We will provide notice before Your Personal\\n\" +\n" +
                        "                        \"Data is transferred and becomes subject to a different Privacy Policy.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Law enforcement  \\n\" +\n" +
                        "                        \"***************\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Under certain circumstances, the Company may be required to disclose Your\\n\" +\n" +
                        "                        \"Personal Data if required to do so by law or in response to valid requests by\\n\" +\n" +
                        "                        \"public authorities (e.g. a court or a government agency).\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Other legal requirements  \\n\" +\n" +
                        "                        \"************************\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"The Company may disclose Your Personal Data in the good faith belief that such\\n\" +\n" +
                        "                        \"action is necessary to:\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"  * Comply with a legal obligation\\n\" +\n" +
                        "                        \"  * Protect and defend the rights or property of the Company\\n\" +\n" +
                        "                        \"  * Prevent or investigate possible wrongdoing in connection with the Service\\n\" +\n" +
                        "                        \"  * Protect the personal safety of Users of the Service or the public\\n\" +\n" +
                        "                        \"  * Protect against legal liability\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Security of Your Personal Data  \\n\" +\n" +
                        "                        \"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"The security of Your Personal Data is important to Us, but remember that no\\n\" +\n" +
                        "                        \"method of transmission over the Internet, or method of electronic storage is\\n\" +\n" +
                        "                        \"100% secure. While We strive to use commercially acceptable means to protect\\n\" +\n" +
                        "                        \"Your Personal Data, We cannot guarantee its absolute security.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Children's Privacy  \\n\" +\n" +
                        "                        \"------------------\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Our Service does not address anyone under the age of 13. We do not knowingly\\n\" +\n" +
                        "                        \"collect personally identifiable information from anyone under the age of 13.\\n\" +\n" +
                        "                        \"If You are a parent or guardian and You are aware that Your child has provided\\n\" +\n" +
                        "                        \"Us with Personal Data, please contact Us. If We become aware that We have\\n\" +\n" +
                        "                        \"collected Personal Data from anyone under the age of 13 without verification\\n\" +\n" +
                        "                        \"of parental consent, We take steps to remove that information from Our\\n\" +\n" +
                        "                        \"servers.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"If We need to rely on consent as a legal basis for processing Your information\\n\" +\n" +
                        "                        \"and Your country requires consent from a parent, We may require Your parent's\\n\" +\n" +
                        "                        \"consent before We collect and use that information.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Links to Other Websites  \\n\" +\n" +
                        "                        \"-----------------------\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Our Service may contain links to other websites that are not operated by Us.\\n\" +\n" +
                        "                        \"If You click on a third party link, You will be directed to that third party's\\n\" +\n" +
                        "                        \"site. We strongly advise You to review the Privacy Policy of every site You\\n\" +\n" +
                        "                        \"visit.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"We have no control over and assume no responsibility for the content, privacy\\n\" +\n" +
                        "                        \"policies or practices of any third party sites or services.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"Changes to this Privacy Policy  \\n\" +\n" +
                        "                        \"------------------------------\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"We may update Our Privacy Policy from time to time. We will notify You of any\\n\" +\n" +
                        "                        \"changes by posting the new Privacy Policy on this page.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"We will let You know via email and/or a prominent notice on Our Service, prior\\n\" +\n" +
                        "                        \"to the change becoming effective and update the \\\"Last updated\\\" date at the top\\n\" +\n" +
                        "                        \"of this Privacy Policy.\\n\" +\n" +
                        "                        \"\\n\" +\n" +
                        "                        \"You are advised to review this Privacy Policy periodically for any changes.\\n\" +\n" +
                        "                        \"Changes to this Privacy Policy are effective when they are posted on this\\n\" +\n" +
                        "                        \"page.\\n\")")
            }
            binding.txtPrivacyArrow.id -> {
                showDialog("Política de privacidad", "Last updated: May 23, 2025\n" +
                        "\n" +
                        "This Privacy Policy describes Our policies and procedures on the collection,\n" +
                        "use and disclosure of Your information when You use the Service and tells You\n" +
                        "about Your privacy rights and how the law protects You.\n" +
                        "\n" +
                        "We use Your Personal data to provide and improve the Service. By using the\n" +
                        "Service, You agree to the collection and use of information in accordance with\n" +
                        "this Privacy Policy. This Privacy Policy has been created with the help of the\n" +
                        "[Free Privacy Policy Generator](https://www.freeprivacypolicy.com/free-\n" +
                        "privacy-policy-generator/).\n" +
                        "Interpretation and Definitions  \n" +
                        "------------------------------\n" +
                        "Interpretation  \n" +
                        "~~~~~~~~~~~~~~\n" +
                        "The words of which the initial letter is capitalized have meanings defined\n" +
                        "under the following conditions. The following definitions shall have the same\n" +
                        "meaning regardless of whether they appear in singular or in plural.\n" +
                        "\n" +
                        "Definitions  \n" +
                        "~~~~~~~~~~~\n" +
                        "For the purposes of this Privacy Policy:\n" +
                        "  * Account means a unique account created for You to access our Service or\n" +
                        "    parts of our Service.\n" +
                        "  * Affiliate means an entity that controls, is controlled by or is under\n" +
                        "    common control with a party, where \"control\" means ownership of 50% or\n" +
                        "    more of the shares, equity interest or other securities entitled to vote\n" +
                        "    for election of directors or other managing authority.\n" +
                        "  * Application refers to ExtreTur, the software program provided by the\n" +
                        "    Company.\n" +
                        "  * Company (referred to as either \"the Company\", \"We\", \"Us\" or \"Our\" in this\n" +
                        "    Agreement) refers to ExtreTur.\n" +
                        "  * Country refers to: Spain\n" +
                        "  * Device means any device that can access the Service such as a computer, a\n" +
                        "    cellphone or a digital tablet.\n" +
                        "  * Personal Data is any information that relates to an identified or\n" +
                        "    identifiable individual.\n" +
                        "  * Service refers to the Application.\n" +
                        "  * Service Provider means any natural or legal person who processes the data\n" +
                        "    on behalf of the Company. It refers to third-party companies or\n" +
                        "    individuals employed by the Company to facilitate the Service, to provide\n" +
                        "    the Service on behalf of the Company, to perform services related to the\n" +
                        "    Service or to assist the Company in analyzing how the Service is used.\n" +
                        "  * Usage Data refers to data collected automatically, either generated by the\n" +
                        "    use of the Service or from the Service infrastructure itself (for example,\n" +
                        "    the duration of a page visit).\n" +
                        "  * You means the individual accessing or using the Service, or the company,\n" +
                        "    or other legal entity on behalf of which such individual is accessing or\n" +
                        "    using the Service, as applicable.\n" +
                        "Collecting and Using Your Personal Data  \n" +
                        "---------------------------------------\n" +
                        "Types of Data Collected  \n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~\n" +
                        "Personal Data  \n" +
                        "*************\n" +
                        "While using Our Service, We may ask You to provide Us with certain personally\n" +
                        "identifiable information that can be used to contact or identify You.\n" +
                        "Personally identifiable information may include, but is not limited to:\n" +
                        "  * Email address\n" +
                        "  * First name and last name\n" +
                        "  * Usage Data\n" +
                        "Usage Data  \n" +
                        "**********\n" +
                        "Usage Data is collected automatically when using the Service.\n" +
                        "Usage Data may include information such as Your Device's Internet Protocol\n" +
                        "address (e.g. IP address), browser type, browser version, the pages of our\n" +
                        "Service that You visit, the time and date of Your visit, the time spent on\n" +
                        "those pages, unique device identifiers and other diagnostic data.\n" +
                        "\n" +
                        "When You access the Service by or through a mobile device, We may collect\n" +
                        "certain information automatically, including, but not limited to, the type of\n" +
                        "mobile device You use, Your mobile device unique ID, the IP address of Your\n" +
                        "mobile device, Your mobile operating system, the type of mobile Internet\n" +
                        "browser You use, unique device identifiers and other diagnostic data.\n" +
                        "\n" +
                        "We may also collect information that Your browser sends whenever You visit our\n" +
                        "Service or when You access the Service by or through a mobile device.\n" +
                        "\n" +
                        "Use of Your Personal Data  \n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                        "The Company may use Personal Data for the following purposes:\n" +
                        "\n" +
                        "  * To provide and maintain our Service , including to monitor the usage of\n" +
                        "    our Service.\n" +
                        "\n" +
                        "  * To manage Your Account: to manage Your registration as a user of the\n" +
                        "    Service. The Personal Data You provide can give You access to different\n" +
                        "    functionalities of the Service that are available to You as a registered\n" +
                        "    user.\n" +
                        "\n" +
                        "  * For the performance of a contract: the development, compliance and\n" +
                        "    undertaking of the purchase contract for the products, items or services\n" +
                        "    You have purchased or of any other contract with Us through the Service.\n" +
                        "\n" +
                        "  * To contact You: To contact You by email, telephone calls, SMS, or other\n" +
                        "    equivalent forms of electronic communication, such as a mobile\n" +
                        "    application's push notifications regarding updates or informative\n" +
                        "    communications related to the functionalities, products or contracted\n" +
                        "    services, including the security updates, when necessary or reasonable for\n" +
                        "    their implementation.\n" +
                        "\n" +
                        "  * To provide You with news, special offers and general information about\n" +
                        "    other goods, services and events which we offer that are similar to those\n" +
                        "    that you have already purchased or enquired about unless You have opted\n" +
                        "    not to receive such information.\n" +
                        "\n" +
                        "  * To manage Your requests: To attend and manage Your requests to Us.\n" +
                        "\n" +
                        "  * For business transfers: We may use Your information to evaluate or conduct\n" +
                        "    a merger, divestiture, restructuring, reorganization, dissolution, or\n" +
                        "    other sale or transfer of some or all of Our assets, whether as a going\n" +
                        "    concern or as part of bankruptcy, liquidation, or similar proceeding, in\n" +
                        "    which Personal Data held by Us about our Service users is among the assets\n" +
                        "    transferred.\n" +
                        "\n" +
                        "  * For other purposes : We may use Your information for other purposes, such\n" +
                        "    as data analysis, identifying usage trends, determining the effectiveness\n" +
                        "    of our promotional campaigns and to evaluate and improve our Service,\n" +
                        "    products, services, marketing and your experience.\n" +
                        "\n" +
                        "\n" +
                        "We may share Your personal information in the following situations:\n" +
                        "\n" +
                        "  * With Service Providers: We may share Your personal information with\n" +
                        "    Service Providers to monitor and analyze the use of our Service, to\n" +
                        "    contact You.\n" +
                        "  * For business transfers: We may share or transfer Your personal information\n" +
                        "    in connection with, or during negotiations of, any merger, sale of Company\n" +
                        "    assets, financing, or acquisition of all or a portion of Our business to\n" +
                        "    another company.\n" +
                        "  * With Affiliates: We may share Your information with Our affiliates, in\n" +
                        "    which case we will require those affiliates to honor this Privacy Policy.\n" +
                        "    Affiliates include Our parent company and any other subsidiaries, joint\n" +
                        "    venture partners or other companies that We control or that are under\n" +
                        "    common control with Us.\n" +
                        "  * With business partners: We may share Your information with Our business\n" +
                        "    partners to offer You certain products, services or promotions.\n" +
                        "  * With other users: when You share personal information or otherwise\n" +
                        "    interact in the public areas with other users, such information may be\n" +
                        "    viewed by all users and may be publicly distributed outside.\n" +
                        "  * With Your consent : We may disclose Your personal information for any\n" +
                        "    other purpose with Your consent.\n" +
                        "\n" +
                        "Retention of Your Personal Data  \n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                        "\n" +
                        "The Company will retain Your Personal Data only for as long as is necessary\n" +
                        "for the purposes set out in this Privacy Policy. We will retain and use Your\n" +
                        "Personal Data to the extent necessary to comply with our legal obligations\n" +
                        "(for example, if we are required to retain your data to comply with applicable\n" +
                        "laws), resolve disputes, and enforce our legal agreements and policies.\n" +
                        "\n" +
                        "The Company will also retain Usage Data for internal analysis purposes. Usage\n" +
                        "Data is generally retained for a shorter period of time, except when this data\n" +
                        "is used to strengthen the security or to improve the functionality of Our\n" +
                        "Service, or We are legally obligated to retain this data for longer time\n" +
                        "periods.\n" +
                        "\n" +
                        "Transfer of Your Personal Data  \n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                        "\n" +
                        "Your information, including Personal Data, is processed at the Company's\n" +
                        "operating offices and in any other places where the parties involved in the\n" +
                        "processing are located. It means that this information may be transferred to —\n" +
                        "and maintained on — computers located outside of Your state, province, country\n" +
                        "or other governmental jurisdiction where the data protection laws may differ\n" +
                        "than those from Your jurisdiction.\n" +
                        "\n" +
                        "Your consent to this Privacy Policy followed by Your submission of such\n" +
                        "information represents Your agreement to that transfer.\n" +
                        "\n" +
                        "The Company will take all steps reasonably necessary to ensure that Your data\n" +
                        "is treated securely and in accordance with this Privacy Policy and no transfer\n" +
                        "of Your Personal Data will take place to an organization or a country unless\n" +
                        "there are adequate controls in place including the security of Your data and\n" +
                        "other personal information.\n" +
                        "\n" +
                        "Delete Your Personal Data  \n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                        "\n" +
                        "You have the right to delete or request that We assist in deleting the\n" +
                        "Personal Data that We have collected about You.\n" +
                        "\n" +
                        "Our Service may give You the ability to delete certain information about You\n" +
                        "from within the Service.\n" +
                        "\n" +
                        "You may update, amend, or delete Your information at any time by signing in to\n" +
                        "Your Account, if you have one, and visiting the account settings section that\n" +
                        "allows you to manage Your personal information. You may also contact Us to\n" +
                        "request access to, correct, or delete any personal information that You have\n" +
                        "provided to Us.\n" +
                        "\n" +
                        "Please note, however, that We may need to retain certain information when we\n" +
                        "have a legal obligation or lawful basis to do so.\n" +
                        "\n" +
                        "Disclosure of Your Personal Data  \n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                        "\n" +
                        "Business Transactions  \n" +
                        "*********************\n" +
                        "\n" +
                        "If the Company is involved in a merger, acquisition or asset sale, Your\n" +
                        "Personal Data may be transferred. We will provide notice before Your Personal\n" +
                        "Data is transferred and becomes subject to a different Privacy Policy.\n" +
                        "\n" +
                        "Law enforcement  \n" +
                        "***************\n" +
                        "\n" +
                        "Under certain circumstances, the Company may be required to disclose Your\n" +
                        "Personal Data if required to do so by law or in response to valid requests by\n" +
                        "public authorities (e.g. a court or a government agency).\n" +
                        "\n" +
                        "Other legal requirements  \n" +
                        "************************\n" +
                        "\n" +
                        "The Company may disclose Your Personal Data in the good faith belief that such\n" +
                        "action is necessary to:\n" +
                        "\n" +
                        "  * Comply with a legal obligation\n" +
                        "  * Protect and defend the rights or property of the Company\n" +
                        "  * Prevent or investigate possible wrongdoing in connection with the Service\n" +
                        "  * Protect the personal safety of Users of the Service or the public\n" +
                        "  * Protect against legal liability\n" +
                        "\n" +
                        "Security of Your Personal Data  \n" +
                        "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n" +
                        "\n" +
                        "The security of Your Personal Data is important to Us, but remember that no\n" +
                        "method of transmission over the Internet, or method of electronic storage is\n" +
                        "100% secure. While We strive to use commercially acceptable means to protect\n" +
                        "Your Personal Data, We cannot guarantee its absolute security.\n" +
                        "\n" +
                        "Children's Privacy  \n" +
                        "------------------\n" +
                        "\n" +
                        "Our Service does not address anyone under the age of 13. We do not knowingly\n" +
                        "collect personally identifiable information from anyone under the age of 13.\n" +
                        "If You are a parent or guardian and You are aware that Your child has provided\n" +
                        "Us with Personal Data, please contact Us. If We become aware that We have\n" +
                        "collected Personal Data from anyone under the age of 13 without verification\n" +
                        "of parental consent, We take steps to remove that information from Our\n" +
                        "servers.\n" +
                        "\n" +
                        "If We need to rely on consent as a legal basis for processing Your information\n" +
                        "and Your country requires consent from a parent, We may require Your parent's\n" +
                        "consent before We collect and use that information.\n" +
                        "\n" +
                        "Links to Other Websites  \n" +
                        "-----------------------\n" +
                        "\n" +
                        "Our Service may contain links to other websites that are not operated by Us.\n" +
                        "If You click on a third party link, You will be directed to that third party's\n" +
                        "site. We strongly advise You to review the Privacy Policy of every site You\n" +
                        "visit.\n" +
                        "\n" +
                        "We have no control over and assume no responsibility for the content, privacy\n" +
                        "policies or practices of any third party sites or services.\n" +
                        "\n" +
                        "Changes to this Privacy Policy  \n" +
                        "------------------------------\n" +
                        "\n" +
                        "We may update Our Privacy Policy from time to time. We will notify You of any\n" +
                        "changes by posting the new Privacy Policy on this page.\n" +
                        "\n" +
                        "We will let You know via email and/or a prominent notice on Our Service, prior\n" +
                        "to the change becoming effective and update the \"Last updated\" date at the top\n" +
                        "of this Privacy Policy.\n" +
                        "\n" +
                        "You are advised to review this Privacy Policy periodically for any changes.\n" +
                        "Changes to this Privacy Policy are effective when they are posted on this\n" +
                        "page.\n")
            }
        }
    }

    private fun showDialog(titulo: String, mensaje: String){
        AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun navBar(){
        binding.navProfile.navLayout.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                return when (item.itemId){
                    binding.navProfile.navLayout.menu.findItem(R.id.inicio).itemId -> {
                        val intent = Intent(applicationContext, MainActivity::class.java)
                        startActivity(intent)
                        true }
                    binding.navProfile.navLayout.menu.findItem(R.id.foro).itemId -> {
                        val user = auth.currentUser

                        if (user == null) {
                            val intent = Intent(applicationContext, LogInActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(applicationContext, ForumActivity::class.java)
                            startActivity(intent)
                        }
                        true }
                    binding.navProfile.navLayout.menu.findItem(R.id.perfil).itemId -> {
                        val user = auth.currentUser

                        if (user == null) {
                            val intent = Intent(applicationContext, LogInActivity::class.java)
                            startActivity(intent)
                        } else {
                            val intent = Intent(applicationContext, ProfileActivity::class.java)
                            startActivity(intent)
                        }
                        true }
                    else -> false }}})
    }
}