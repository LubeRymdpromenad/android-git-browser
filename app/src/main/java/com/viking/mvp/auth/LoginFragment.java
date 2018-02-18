package com.viking.mvp.auth;


import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.viking.MainActivity;
import com.viking.R;
import com.viking.api.UserRepository;
import com.viking.databinding.FragmentLoginBinding;

/**
 * Created by lars@harbourfront.se
 */

public class LoginFragment extends Fragment implements LoginView {
    private LoginPresenter mPresenter;
    private FragmentLoginBinding mBinding;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState );

        final SharedPreferences sharedPreferences = getActivity().getApplicationContext()
                .getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);

        final UserRepository userRepository = new UserRepository(sharedPreferences);
        final LoginModel loginModel = new LoginModel(userRepository);

        mPresenter = new LoginPresenter(this, loginModel);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        mBinding.btnLogin.setOnClickListener(view12 -> {
            final String userName1 = mBinding.tvUsername.getText().toString();
            final String password1 = mBinding.tvPassword.getText().toString();
            mPresenter.onLoginClicked(userName1, password1);

            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            assert imm != null;
            imm.hideSoftInputFromWindow(mBinding.tvPassword.getWindowToken(), 0);
        });

        mBinding.flForgotPwdContainer.setOnClickListener(view1 -> mPresenter.onForgotPasswordClicked());
    }

    public static LoginFragment newInstance() {
        final LoginFragment fragment = new LoginFragment();
        fragment.setArguments(new Bundle());
        return fragment;
    }

    @Override
    public void openRepoListView() {
        if (getActivity() != null) {
            ((MainActivity) getActivity()).openRepoListView();
        }
    }

    @Override
    public void displayError() {
        Toast.makeText(getContext(), R.string.wrong_password_message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displayForgotPassword() {
        Toast.makeText(getContext(), R.string.forgot_password_message, Toast.LENGTH_LONG).show();
    }
}
