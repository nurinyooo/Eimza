package com.ders.Pades;

import com.ders.SmartCard.SmartCardManager;
import tr.gov.tubitak.uekae.esya.api.asn.x509.ECertificate;
import tr.gov.tubitak.uekae.esya.api.common.crypto.BaseSigner;
import tr.gov.tubitak.uekae.esya.api.common.util.LicenseUtil;
import tr.gov.tubitak.uekae.esya.api.pades.PAdESContext;
import tr.gov.tubitak.uekae.esya.api.signature.*;
import tr.gov.tubitak.uekae.esya.api.signature.config.Config;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PadesSign {
    public PadesSign() {
    }

    public  void signPades(JFileChooser j, JFileChooser j2) throws Exception{

        LicenseUtil.setLicenseXml(new FileInputStream("C:\\Users\\pc\\Desktop\\ESYA" + "\\lisans\\lisans.xml"));

        Date expirationDate = LicenseUtil.getExpirationDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("License expiration date :" + dateFormat.format(expirationDate));
        SignatureContainer signatureContainer = SignatureFactory.readContainer(SignatureFormat.PAdES,
                new FileInputStream(String.valueOf(j.getSelectedFile().getPath())), createContext());

        ECertificate eCertificate = SmartCardManager.getInstance().getSignatureCertificate(true);
        BaseSigner signer = SmartCardManager.getInstance().getSigner("1234", eCertificate);

        // add signature
        Signature signature = signatureContainer.createSignature(eCertificate);
        signature.setSigningTime(Calendar.getInstance());
        signature.sign(signer);
        signatureContainer.write(new FileOutputStream(j.getSelectedFile().getPath()));

        // read and validate
        SignatureContainer readContainer = SignatureFactory.readContainer(SignatureFormat.PAdES,
                new FileInputStream(String.valueOf(j.getSelectedFile().getPath())), createContext());

        ContainerValidationResult validationResult = readContainer.verifyAll();
        System.out.println(validationResult);


        SignatureContainer sc = SignatureFactory.readContainer(SignatureFormat.PAdES,
                new FileInputStream(String.valueOf(j.getSelectedFile().getPath())), createContext());

        ContainerValidationResult validationResult1 = sc.verifyAll();
        System.out.println(validationResult1);

        SignatureContainer signatureContainer1 = SignatureFactory.readContainer(SignatureFormat.PAdES,
                new FileInputStream(j.getSelectedFile().getPath()), createContext());

        signatureContainer1.write(new FileOutputStream(j2.getSelectedFile().getPath()+".pdf"));



    }

    protected  static PAdESContext createContext() {
        PAdESContext c = new PAdESContext(new File("C:\\Users\\pc\\Desktop\\ESYA\\testdata\\").toURI());
        c.setConfig(new Config("C:\\Users\\pc\\Desktop\\ESYA" + "\\config\\esya-signature-config.xml"));
        return c;
    }
}
