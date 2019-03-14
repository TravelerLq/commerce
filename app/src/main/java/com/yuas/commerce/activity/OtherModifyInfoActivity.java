package com.yuas.commerce.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


import com.yuas.commerce.R;
import com.yuas.commerce.bean.EnterpriseInfoBean;
import com.yuas.commerce.bean.ModifyEnterpriseRequestBean;
import com.yuas.commerce.constant.AppConstant;

import butterknife.BindView;

/**
 * Created by liqing on 2018/12/28.
 */

public class OtherModifyInfoActivity extends BaseActivity {

    @BindView(R.id.button_back)
    ImageButton backBtn;
    @BindView(R.id.textview_title)
    TextView titleTxt;

    @BindView(R.id.edt_company_name)
    EditText edtCompanyName;

    @BindView(R.id.edt_company_nature)
    EditText edtCompanyNature;

    //
    @BindView(R.id.edt_company_vat)
    EditText edtCompanyVat;

    @BindView(R.id.edt_company_income)
    EditText edtCompanyIncome;

    @BindView(R.id.edt_company_tax)
    EditText edtCompanyTax;

    @BindView(R.id.edt_company_bank)
    EditText edtCompanyBank;

    @BindView(R.id.edt_company_bank_account)
    EditText edtCompanyBanAccount;

    @BindView(R.id.edt_company_address)
    EditText edtCompanyAddress;

    @BindView(R.id.edt_company_invoice)
    EditText edtCompanyInvoice;

    @BindView(R.id.edt_company_legal_name)
    EditText edtCompanyLegalName;

    @BindView(R.id.edt_company_legal_id)
    EditText edtCompanyLegalId;

    @BindView(R.id.tv_sure)
    TextView tvSure;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_modify_info_layout);
        initViewEvent();
        titleTxt.setText(getResources().getString(R.string.other_modify));
        EnterpriseInfoBean intentBean = (EnterpriseInfoBean) getIntent().getExtras().getSerializable(AppConstant.SERIAL_KEY);
        if (intentBean != null) {
            initdata(intentBean);
        }

    }


    private void initdata(EnterpriseInfoBean intentBean) {
        edtCompanyName.setText(intentBean.getCompanyName());
        edtCompanyNature.setText(intentBean.getCompanyNature());
        ///增值税征收方式 vat_
        edtCompanyVat.setText(intentBean.getValueAddedTaxMethod());
        //-所得税征收方式income
        edtCompanyIncome.setText(intentBean.getIncomeTaxCollectionMethod());
        //税号taxId
        edtCompanyTax.setText(intentBean.getTaxNumber());
//
        edtCompanyAddress.setText(intentBean.getAddress());
        edtCompanyBanAccount.setText(intentBean.getBankAccount());
        edtCompanyBank.setText(intentBean.getBank());
        //开票方式
        edtCompanyInvoice.setText(intentBean.getBillingMethod());
        //法人
        edtCompanyLegalName.setText(intentBean.getLegalName());
        //法人身份证号
        edtCompanyLegalId.setText(intentBean.getLegalIdNumber());


    }

    @Override
    protected void initViewEvent() {
        tvSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        cheData();
        super.onClick(view);

//        if (view.getId() == R.id.tv_sure) {
//            cheData();
//        }else if(view.getId()==R.id.button_back){
//
//        }
    }

    private void cheData() {
        ModifyEnterpriseRequestBean enterpriseInfoBean = new ModifyEnterpriseRequestBean();

        String companyNameStr = edtCompanyName.getText().toString();
        String companyNatureStr = edtCompanyNature.getText().toString();
        String companyVatStr = edtCompanyVat.getText().toString();
        String companyIncome = edtCompanyIncome.getText().toString();
        String companyTaxId = getEdtStr(edtCompanyTax);
        String companyAddress = getEdtStr(edtCompanyAddress);
        String companyCompanyBankAccount = getEdtStr(edtCompanyBanAccount);
        String companyCompanyBank = getEdtStr(edtCompanyBank);

        String companyInvoice = getEdtStr(edtCompanyInvoice);
        String companyLegalName = getEdtStr(edtCompanyLegalName);
        String companyLegalId = getEdtStr(edtCompanyLegalId);


        enterpriseInfoBean.setCompanyName(companyNameStr);
        enterpriseInfoBean.setCompanyNature(companyNatureStr);
        enterpriseInfoBean.setValueAddedTaxMethod(companyVatStr);
        enterpriseInfoBean.setIncomeTaxCollectionMethod(companyIncome);
        enterpriseInfoBean.setTaxNumber(companyTaxId);

        enterpriseInfoBean.setAddress(companyAddress);
        enterpriseInfoBean.setBankAccount(companyCompanyBankAccount);
        enterpriseInfoBean.setBank(companyCompanyBank);

        enterpriseInfoBean.setBillingMethod(companyInvoice);
        enterpriseInfoBean.setLegalName(companyLegalName);
        enterpriseInfoBean.setLegalIdNumber(companyLegalId);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstant.SERIAL_KEY, enterpriseInfoBean);
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();

    }

    private String getEdtStr(EditText editText) {
        return editText.getText().toString();
    }

    //检测返回按钮


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            cheData();
        }
        return super.onKeyDown(keyCode, event);
    }
}
