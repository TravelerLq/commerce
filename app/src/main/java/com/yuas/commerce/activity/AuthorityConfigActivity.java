package com.yuas.commerce.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.yuas.commerce.R;
import com.yuas.commerce.adapter.AuthorityConfigGridAdapter;
import com.yuas.commerce.adapter.PositionRecycleAdapter;
import com.yuas.commerce.bean.AuthorityItemBean;
import com.yuas.commerce.bean.AuthorityRequestBean;
import com.yuas.commerce.bean.AuthorityResponseBean;
import com.yuas.commerce.bean.PersonBean;
import com.yuas.commerce.bean.PositionBean;
import com.yuas.commerce.constant.MySpEdit;
import com.yuas.commerce.network.control.AuthorityConfigControl;
import com.yuas.commerce.observer.CommonDialogObserver;
import com.yuas.commerce.observer.RxHelper;
import com.yuas.commerce.utils.Loger;
import com.yuas.commerce.utils.PopupWindowUtil;
import com.yuas.commerce.view.LinearLayoutColorDivider;
import com.yuas.commerce.view.ScrollGridView;
import com.yuas.commerce.view.SimpleToast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;

/**
 * Created by liqing on 2018/12/17.
 */

public class AuthorityConfigActivity extends BaseActivity {

    //选择权限配置对象
    @BindView(R.id.tv_config_target)
    TextView tvConfigTarget;

    //
    @BindView(R.id.tv_select_positon)
    TextView tvSelectPosition;

    @BindView(R.id.rl_authority_carousel)
    RelativeLayout rlAuthorityCarousel;

    //父布局的选择框
    @BindView(R.id.iv_carousel_select)
    ImageView ivCourselSelect;

    // 企业展
    @BindView(R.id.grid_corporation)
    ScrollGridView gridCorporation;
    //企业展选择按钮
    @BindView(R.id.rl_corporation)
    RelativeLayout rlCorporation;
    @BindView(R.id.iv_select_coporation)
    ImageView ivSelectCorporation;

    //新闻
    @BindView(R.id.grid_news)
    ScrollGridView gridNews;
    //新闻选择按钮
    @BindView(R.id.iv_select_news)
    ImageView ivSelectNews;
    @BindView(R.id.rl_news)
    RelativeLayout rlNews;

    //学习互动
    @BindView(R.id.grid_learning)
    ScrollGridView gridLearning;
    @BindView(R.id.iv_select_learning)
    ImageView ivSelectLearning;
    @BindView(R.id.rl_learn)
    RelativeLayout rlLearn;

    //商会介绍
    @BindView(R.id.grid_introducing)
    ScrollGridView gridIntroducing;
    @BindView(R.id.iv_select_introduce)
    ImageView ivSelectIntroduce;
    @BindView(R.id.rl_introduce)
    RelativeLayout rlIntroduce;

    //投资中心
    @BindView(R.id.grid_invest)
    ScrollGridView gridInvest;
    @BindView(R.id.iv_select_invest)
    ImageView ivSelectInvest;
    @BindView(R.id.rl_invest)
    RelativeLayout rlInvest;
    //返回按钮
    @BindView(R.id.button_back)
    ImageButton buttonBack;
    //标题
    @BindView(R.id.textview_title)
    TextView tvTitle;

    @BindView(R.id.btn_sure)
    TextView btnSure;

    @BindView(R.id.tv_go_search)
    TextView tvGoSearch;

    // 初始状态
    boolean isCarouselSelect = false;
    boolean isCoporationSelect = false;
    boolean isNewsSelect = false;
    boolean isLearnSelect = false;
    boolean isIntroduceSelect = false;
    boolean isInvestSelect = false;

    private static int REQUEST_CODE_NAME = 3001;
    AuthorityConfigGridAdapter corporationAdapter;
    AuthorityConfigGridAdapter newsAdapter;
    AuthorityConfigGridAdapter learnAdapter;
    AuthorityConfigGridAdapter introduceAdapter;
    AuthorityConfigGridAdapter investAdapter;

    List<AuthorityItemBean> coporationList = new ArrayList<>();
    List<AuthorityItemBean> newsList = new ArrayList<>();
    List<AuthorityItemBean> learnList = new ArrayList<>();
    List<AuthorityItemBean> introduceList = new ArrayList<>();
    List<AuthorityItemBean> investList = new ArrayList<>();

    private Context context;
    //    String url1 = "http://i.imgur.com/0gqnEaY.jpg";
//    String url2 = "https://tvax1.sinaimg.cn/crop.23.13.554.554.180/006JLbDuly8ftf9s64z2hj30gp0gojro.jpg";
//    String url3 = "https://tva4.sinaimg.cn/crop.40.9.713.713.180/005vApKGtw1ee51viuhktj30m80m1763.jpg";
//    String url4 = "https://tvax2.sinaimg.cn/crop.0.0.467.467.180/92220d38ly8fduilbovyuj20d00d3jy9.jpg";
//    String url5 = "https://tvax2.sinaimg.cn/crop.14.0.291.291.180/006jAFZ6ly8fmtyefbjouj30a7083wef.jpg";
    private PopupWindow mPopupWindow;
    private int tvSelectWidth;
    private String[] arrayConfigTarget;
    private PositionRecycleAdapter mAdapter;
    private List<PositionBean> listPositions = new ArrayList<>();
    private String commerceId;
    private List<AuthorityResponseBean> listAuthoritys = new ArrayList<>();
    private String selectPosId;
    private String selectUserId;
    private String submitAuthorityType;
    private String authorityIds = "";
    private String coporationId;
    private String newsId;
    private String learnId;
    private String introduceId;
    private String investId;
    private String carouselId;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_config_layout);
        context = AuthorityConfigActivity.this;
        commerceId = MySpEdit.getInstance().getCommerceId();
        Loger.e("---commerceId--" + commerceId);
// git
        tvTitle.setText(getResources().getString(R.string.authority_config));
        arrayConfigTarget = getResources().getStringArray(R.array.config_target);
        initViewEvent();
        // initData();
        getData();
        getPositions();
        //优秀企业展
        corporationAdapter = new AuthorityConfigGridAdapter(coporationList, context);
        gridCorporation.setAdapter(corporationAdapter);
        //热点新闻
        newsAdapter = new AuthorityConfigGridAdapter(newsList, context);
        gridNews.setAdapter(newsAdapter);
        //学习互动
        learnAdapter = new AuthorityConfigGridAdapter(learnList, context);
        gridLearning.setAdapter(learnAdapter);
        //商会介绍
        introduceAdapter = new AuthorityConfigGridAdapter(introduceList, context);
        gridIntroducing.setAdapter(introduceAdapter);
        //投资融资中心
        investAdapter = new AuthorityConfigGridAdapter(investList, context);
        gridInvest.setAdapter(investAdapter);

        //初始化默认选择状态
        // setSelect(isCoporationSelect, ivSelectCorporation, corporationAdapter, coporationList,);

        //优秀企业展Item点击
        gridCorporation.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AuthorityItemBean bean = coporationList.get(position);
                bean.setResourcesChecked(!bean.isResourcesChecked());
                corporationAdapter.notifyDataSetChanged();
                setParentSelector(bean, ivSelectCorporation, coporationList);
            }
        });


        //热点新闻Item点击
        gridNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AuthorityItemBean bean = newsList.get(position);
                bean.setResourcesChecked(!bean.isResourcesChecked());
                newsAdapter.notifyDataSetChanged();
                setParentSelector(bean, ivSelectNews, newsList);
            }
        });


        //学习互动Item点击
        gridLearning.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                AuthorityItemBean bean = learnList.get(position);
                bean.setResourcesChecked(!bean.isResourcesChecked());
                learnAdapter.notifyDataSetChanged();
                setParentSelector(bean, ivSelectLearning, learnList);
            }
        });

        //商会介绍Item点击
        gridIntroducing.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AuthorityItemBean bean = introduceList.get(position);
                bean.setResourcesChecked(!bean.isResourcesChecked());
                introduceAdapter.notifyDataSetChanged();
                setParentSelector(bean, ivSelectIntroduce, introduceList);
            }
        });

        //投资融资中心
        gridInvest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AuthorityItemBean bean = investList.get(position);
                bean.setResourcesChecked(!bean.isResourcesChecked());
                investAdapter.notifyDataSetChanged();
                setParentSelector(bean, ivSelectInvest, investList);


            }
        });

    }

    private void setParentSelector(AuthorityItemBean bean, ImageView ivSelectInvest, List<AuthorityItemBean> investList) {
        if (bean.isResourcesChecked()) {
            ivSelectInvest.setImageResource(R.drawable.ic_select);
        } else {
            for (int i = 0; i < investList.size(); i++) {
                AuthorityItemBean bean1 = investList.get(i);
                if (bean1.isResourcesChecked()) {
                    ivSelectInvest.setImageResource(R.drawable.ic_select);
                    break;
                } else {
                    ivSelectInvest.setImageResource(R.drawable.ic_unselect);
                }
            }
        }
    }

    //设置 全选or全不选
    private void setSelect(boolean isSelector, ImageView imageView,
                           AuthorityConfigGridAdapter adapter, List<AuthorityItemBean> listData) {


        if (isSelector) {
            //全选
            imageView.setImageResource(R.drawable.ic_select);


        } else {
            imageView.setImageResource(R.drawable.ic_unselect);
        }
        Loger.e("listData traveral:--" + System.currentTimeMillis());
        for (int i = 0; i < listData.size(); i++) {
            listData.get(i).setResourcesChecked(isSelector);
        }
        Loger.e("listData traveral:--" + System.currentTimeMillis());
        adapter.notifyDataSetChanged();
    }


    private void initData() {

        //轮播
        initParentAuthority(listAuthoritys.get(1), ivCourselSelect);
        carouselId = listAuthoritys.get(1).getId();
        if (listAuthoritys.get(1).isResourcesChecked()) {
            isCarouselSelect = true;
        } else {
            isCarouselSelect = false;
        }

        initParentAuthority(listAuthoritys.get(0), ivSelectCorporation);
        coporationList.clear();
        coporationList.addAll(listAuthoritys.get(0).getResourcesVoList());
        coporationId = listAuthoritys.get(0).getId();
        corporationAdapter.notifyDataSetChanged();


        //热点新闻    商会介绍  投融资中心
        newsList.clear();
        initParentAuthority(listAuthoritys.get(2), ivSelectNews);
        newsList.addAll(listAuthoritys.get(2).getResourcesVoList());
        newsId = listAuthoritys.get(2).getId();
        newsAdapter.notifyDataSetChanged();
        //互动学习
        initParentAuthority(listAuthoritys.get(3), ivSelectLearning);
        learnList.clear();
        learnList.addAll(listAuthoritys.get(3).getResourcesVoList());
        learnId = listAuthoritys.get(3).getId();
        learnAdapter.notifyDataSetChanged();

        //商会介绍
        initParentAuthority(listAuthoritys.get(4), ivSelectIntroduce);
        introduceList.clear();
        introduceList.addAll(listAuthoritys.get(4).getResourcesVoList());
        introduceId = listAuthoritys.get(4).getId();
        introduceAdapter.notifyDataSetChanged();
        //投融资中心
        initParentAuthority(listAuthoritys.get(5), ivSelectInvest);
        investList.clear();
        investList.addAll(listAuthoritys.get(5).getResourcesVoList());
        investId = listAuthoritys.get(5).getId();
        investAdapter.notifyDataSetChanged();
//        for (int i = 0; i < 6; i++) {
//            AuthorityItemBean beanInvest = new AuthorityItemBean();
//            beanInvest.setId(i + "");
//            beanInvest.setTitle("pos=" + i);
//            beanInvest.setSelected(false);
//            if (i % 3 == 0) {
//                beanInvest.setUrl(url5);
//            } else if (i % 3 == 1) {
//                beanInvest.setUrl(url4);
//            }
//
//            investList.add(beanInvest);
//        }

        //职位获取：
//        String[] posArray = getResources().getStringArray(R.array.country_names);
//        for (int i = 0; i < 20; i++) {
//            PositionBean bean1 = new PositionBean();
//            bean1.setName(posArray[i]);
//            listPositions.add(i, bean1);
//        }


    }

    private void initParentAuthority(AuthorityResponseBean authorityResponseBean, ImageView ivSelectCorporation) {


        if (authorityResponseBean.isResourcesChecked()) {
            ivSelectCorporation.setImageResource(R.drawable.ic_select);

        } else {
            ivSelectCorporation.setImageResource(R.drawable.ic_unselect);
        }
    }


    @Override
    protected void initViewEvent() {
        tvConfigTarget.setOnClickListener(this);
        tvSelectPosition.setOnClickListener(this);
        tvGoSearch.setOnClickListener(this);
        rlAuthorityCarousel.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        tvGoSearch.setOnClickListener(this);
        rlCorporation.setOnClickListener(this);
        rlNews.setOnClickListener(this);
        rlLearn.setOnClickListener(this);
        rlIntroduce.setOnClickListener(this);
        rlInvest.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_config_target:
                Loger.e("---choose target");
                //type= 0 选择权限配置对象
                showPopWindow(tvConfigTarget, 0, "0");
                //  toActivity(RemotelyBillingRecycleActivity.class);
                break;
            case R.id.tv_select_positon:
                // type= 1 选择职位
                showPopWindow(tvSelectPosition, 0, "1");
                break;
            case R.id.tv_go_search:
                //  toActivity(SearchPersonActivity.class);
                toActivityWithType(SearchPersonActivity.class, "name", REQUEST_CODE_NAME, true);
                break;

            case R.id.rl_authority_carousel:
                isCarouselSelect = !isCarouselSelect;
                if (isCarouselSelect) {
                    ivCourselSelect.setImageResource(R.drawable.ic_select);

                } else {
                    ivCourselSelect.setImageResource(R.drawable.ic_unselect);
                }
                break;
            case R.id.btn_sure:

                submitAuthority(submitAuthorityType);

                break;

            case R.id.rl_corporation:
                //优秀企业
                isCoporationSelect = !isCoporationSelect;
                setSelect(isCoporationSelect, ivSelectCorporation, corporationAdapter, coporationList);
                break;
            case R.id.rl_news:
                //热点新闻
                isNewsSelect = !isNewsSelect;
                setSelect(isNewsSelect, ivSelectNews, newsAdapter, newsList);
                break;
            case R.id.rl_learn:
                //互动学习
                isLearnSelect = !isLearnSelect;
                setSelect(isLearnSelect, ivSelectLearning, learnAdapter, learnList);
                break;
            case R.id.rl_introduce:
                //商会介绍
                isIntroduceSelect = !isIntroduceSelect;
                setSelect(isIntroduceSelect, ivSelectIntroduce, introduceAdapter, introduceList);
                break;
            case R.id.rl_invest:
                isInvestSelect = !isInvestSelect;
                setSelect(isInvestSelect, ivSelectInvest, investAdapter, investList);
                break;
            default:
                break;
        }

    }


    private String getIds(List<AuthorityItemBean> list, String selectParentId) {
        Boolean hasItemSelect = false;

        //否则
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isResourcesChecked()) {
                hasItemSelect = true;
                if (!TextUtils.isEmpty(authorityIds)) {
                    authorityIds = authorityIds + "," + list.get(i).getId();

                } else {
                    authorityIds = list.get(i).getId();
                }
            }
        }

        if (hasItemSelect) {
            authorityIds = authorityIds + "," + selectParentId;
        }


        return authorityIds;
    }

    private void getAuthorityIds() {

        for (int i = 0; i < investList.size(); i++) {
            AuthorityItemBean bean = investList.get(i);
            if (bean.isResourcesChecked()) {
                if (TextUtils.isEmpty(authorityIds)) {
                    authorityIds = authorityIds + "," + bean.getId();
                } else {
                    authorityIds = bean.getId();
                }

            }
        }

        if (!TextUtils.isEmpty(coporationId)) {
            authorityIds = splicingStr(corporationAdapter.getSelectIds(), coporationId, authorityIds);
        }
        if (!TextUtils.isEmpty(newsId)) {
            authorityIds = splicingStr(newsAdapter.getSelectIds(), newsId, authorityIds);
        }
        if (!TextUtils.isEmpty(introduceId)) {
            authorityIds = splicingStr(introduceAdapter.getSelectIds(), introduceId, authorityIds);
        }

        if (!TextUtils.isEmpty(investId)) {
            authorityIds = splicingStr(investAdapter.getSelectIds(), investId, authorityIds);
        }

        Loger.e("--authorityIds" + authorityIds);
    }


    //数据返回
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_NAME) {
            PersonBean personBean = (PersonBean) data.getExtras().getSerializable("person");
            tvGoSearch.setText(personBean.getUserName());
            selectUserId = personBean.getUserId();
            initAuthorityByName();
        }
    }

    //从网络获取数据
    public void getData() {
        AuthorityRequestBean bean = null;
        Observable<List<AuthorityResponseBean>> observable = new AuthorityConfigControl().getAuthority("1");
        CommonDialogObserver<List<AuthorityResponseBean>> observer =
                new CommonDialogObserver<List<AuthorityResponseBean>>(this) {

                    @Override
                    public void onNext(List<AuthorityResponseBean> authorityList) {
                        super.onNext(authorityList);
                        listAuthoritys.clear();
                        listAuthoritys.addAll(authorityList);
                        initData();

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, AuthorityConfigActivity.this);

    }

    //获取职位表
    private void getPositions() {
        Observable<List<PositionBean>> observable = new AuthorityConfigControl().getPostions(commerceId);
        CommonDialogObserver<List<PositionBean>> observer = new CommonDialogObserver<List<PositionBean>>(this) {

            @Override
            public void onNext(List<PositionBean> positionBeans) {
                super.onNext(positionBeans);
                if (positionBeans != null) {
                    listPositions.clear();
                    listPositions.addAll(positionBeans);
                }
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
            }
        };
        RxHelper.bindOnUIActivityLifeCycle(observable, observer, AuthorityConfigActivity.this);
    }


    //提交数据
    public void submitAuthority(String type) {

        getIds(investList, investId);
        getIds(introduceList, introduceId);
        getIds(learnList, learnId);
        getIds(coporationList, coporationId);
        getIds(newsList, newsId);
        if (isCarouselSelect) {
            if (!TextUtils.isEmpty(authorityIds)) {
                authorityIds = authorityIds + "," + carouselId;
            } else {
                authorityIds = carouselId;
            }
        }
        Loger.e("---authrotyIds=" + authorityIds);

        AuthorityRequestBean requestBean = new AuthorityRequestBean();
        if (submitAuthorityType == null) {
            SimpleToast.toastMessage("请先选择配置对象", Toast.LENGTH_SHORT);
            return;
        }

        Loger.e("--authorityIds" + authorityIds);
        requestBean.setResourcesId(authorityIds);
        if (type.equals("0")) {
            //按照职位配置权限 需要 body:roleId、resourcesId
            if (selectPosId == null) {
                SimpleToast.toastMessage("请选择职务", Toast.LENGTH_SHORT);
                return;
            }
            requestBean.setRoleId(selectPosId);

        } else {
            //按照姓名权限
//            {"userId":"580",
//                    "resourcesId":"1,2",
//
//                    "commerceId":1
//            }
            if (selectUserId == null) {
                SimpleToast.toastMessage("请选择姓名", Toast.LENGTH_SHORT);
                return;
            }
            requestBean.setUserId(selectUserId);
            requestBean.setCommerceId(commerceId);

        }
        Observable<Boolean> observable = new AuthorityConfigControl().submitAuthority(requestBean, type);
        CommonDialogObserver<Boolean> observer = new CommonDialogObserver<Boolean>(this) {
            @Override
            public void onNext(Boolean aBoolean) {
                super.onNext(aBoolean);
                if (aBoolean) {
                    //  Toast.makeText(context, "权限配置成功", Toast.LENGTH_SHORT).show();
                    SimpleToast.toastMessage(getResources().getString(R.string.config_success), Toast.LENGTH_SHORT);
                    finish();
                } else {
                    SimpleToast.toastMessage(getResources().getString(R.string.config_failed), Toast.LENGTH_SHORT);
//
                }

            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                SimpleToast.toastMessage(getResources().getString(R.string.config_failed), Toast.LENGTH_SHORT);
//
            }
        };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, AuthorityConfigActivity.this);

    }

    //如果子类一个没有选，则直接返回空
    private String splicingStr(String selectIds, String coporationId, String authorityIds) {

        if (!TextUtils.isEmpty(selectIds)) {
            if (!TextUtils.isEmpty(authorityIds)) {
                return selectIds + "," + coporationId + "," + authorityIds;
            }
            return selectIds + "," + coporationId;

        }
        return authorityIds;


    }

    private View getPopupWindowContentView(final int pos, String type) {
        View contentView = null;
        if (type.equals("0")) {
            contentView = LayoutInflater.from(this).inflate(R.layout.dialog_menu_billing, null);
            View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (view.getId() == R.id.tv_contract_selection) {
                        //为职位配置权限
                        submitAuthorityType = "0";
                        tvConfigTarget.setText(arrayConfigTarget[0]);
                        tvSelectPosition.setVisibility(View.VISIBLE);
                        tvGoSearch.setVisibility(View.GONE);

                    } else {
                        //为姓名配置权限
                        submitAuthorityType = "1";
                        tvConfigTarget.setText(arrayConfigTarget[1]);
                        tvSelectPosition.setVisibility(View.GONE);
                        tvGoSearch.setVisibility(View.VISIBLE);
                    }

                    mPopupWindow.dismiss();
                }
            };

            TextView tvConfigPosition = (TextView) contentView.findViewById(R.id.tv_contract_selection);
            TextView tvConfigName = (TextView) contentView.findViewById(R.id.tv_no_contract_selection);
            tvConfigPosition.setText(arrayConfigTarget[0]);
            tvConfigName.setText(arrayConfigTarget[1]);
            tvConfigPosition.setOnClickListener(menuItemOnClickListener);
            tvConfigName.setOnClickListener(menuItemOnClickListener);
        } else {
            contentView = LayoutInflater.from(this).inflate(R.layout.dialog_recycelview, null);

            View.OnClickListener menuItemOnClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mPopupWindow.dismiss();
                }
            };

            RecyclerView recyclerViewPositions = (RecyclerView) contentView.findViewById(R.id.recycle_positions);

            LinearLayoutManager linearLayoutManager =
                    new LinearLayoutManager(context, LinearLayout.VERTICAL, false);
            linearLayoutManager.setRecycleChildrenOnDetach(false);
            recyclerViewPositions.setLayoutManager(linearLayoutManager);
            // String[] strings = getResources().getStringArray(R.array.country_names);

            recyclerViewPositions.addItemDecoration(new LinearLayoutColorDivider(getResources(), R.color.grey_f0f0f3, R.dimen.dimen_1, DividerItemDecoration.VERTICAL));
            mAdapter = new PositionRecycleAdapter(context, listPositions);
            recyclerViewPositions.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new PositionRecycleAdapter.OnItemClickListener() {
                @Override
                public void OnItemClick(View view, int pos) {
                    Loger.e("position item click---");
                    tvSelectPosition.setText(listPositions.get(pos).getRoleName());
                    selectPosId = listPositions.get(pos).getId();
                    // type ="position"为职位配置权限
                    initAuthorityByPosition(selectPosId);
                    mPopupWindow.dismiss();
                }
            });
        }
        return contentView;
    }

    //按职位 初始化权限配置
    private void initAuthorityByPosition(String selectPosId) {
        Observable<List<AuthorityResponseBean>> observable = new AuthorityConfigControl().getAuthorityByPosition("1", selectPosId);
        CommonDialogObserver<List<AuthorityResponseBean>> observer =
                new CommonDialogObserver<List<AuthorityResponseBean>>(this) {

                    @Override
                    public void onNext(List<AuthorityResponseBean> authorityList) {
                        super.onNext(authorityList);
                        listAuthoritys.clear();
                        listAuthoritys.addAll(authorityList);
                        initData();

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                    }
                };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, AuthorityConfigActivity.this);

    }

    private void initAuthorityByName() {
        Observable<List<AuthorityResponseBean>> observable = new AuthorityConfigControl().getAuthoritByName("1", selectUserId, commerceId);
        CommonDialogObserver<List<AuthorityResponseBean>> observer =
                new CommonDialogObserver<List<AuthorityResponseBean>>(this) {

                    @Override
                    public void onNext(List<AuthorityResponseBean> authorityList) {
                        super.onNext(authorityList);
                        listAuthoritys.clear();
                        listAuthoritys.addAll(authorityList);
                        initData();

                    }

                    @Override
                    public void onError(Throwable t) {
                        super.onError(t);
                        Loger.e("----nameAuth-onError--" + t.getMessage());
                    }
                };

        RxHelper.bindOnUIActivityLifeCycle(observable, observer, AuthorityConfigActivity.this);

    }

    //select
    private void showPopWindow(View anchorView, int pos, String type) {
        View contentView = getPopupWindowContentView(pos, type);
        if (type.equals("0")) {
            mPopupWindow = new PopupWindow(contentView, tvSelectWidth
                    , ViewGroup.LayoutParams.WRAP_CONTENT, true);
        } else {
            mPopupWindow = new PopupWindow(contentView, tvSelectWidth
                    , ViewGroup.LayoutParams.WRAP_CONTENT, true);
        }


        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        // 设置好参数之后再show
        int windowPos[] = PopupWindowUtil.calculatePopWindowPos(anchorView, contentView);
        //  int xOff = 20; // 可以自己调整偏移
        int xOff = 0; // 可以自己调整偏移
        windowPos[0] -= xOff;
        //  mPopupWindow.showAtLocation(anchorView, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);
        mPopupWindow.showAsDropDown(anchorView, 0, 2);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus) {
            tvSelectWidth = tvConfigTarget.getMeasuredWidth();
            Loger.e("tvSelectWidth-=" + tvSelectWidth);
        }
    }


}
