package tk.beason.noah.modules.main.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import tk.beason.common.utils.router.AppRouter
import tk.beason.noah.R
import tk.beason.noah.entries.UserContainer
import tk.beason.noah.modules.base.AppBaseFragment
import tk.beason.noah.modules.settings.SettingsActivity
import kotlinx.android.synthetic.main.fragement_mine.*

/**
 * Created by beasontk on 2017/4/21.
 * Home Fragment
 */

class MineFragment : AppBaseFragment(), MineContract.View, View.OnClickListener {


    private var mRootView: View? = null

    private lateinit var mPresenter: MineContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView != null) {
            return mRootView
        }

        mRootView = inflater.inflate(R.layout.fragement_mine, container, false)

        mPresenter = MinePresenter(this)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settings.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.onResume(context)
    }


    override fun update(userContainer: tk.beason.noah.entries.UserContainer?) {
        /*
        ImageLoader.with(getContext())
                .load(userContainer.getUser().getAvatar())
                .mode(ImageLoader.LoaderMode.CIRCLE_CROP)
                .placeholder(R.drawable.logo)
                .into(mAvatarView);


        mNameView.setText(userContainer.getUser().getNickName());
        */
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.settings -> {
                AppRouter.with(context)
                        .target(SettingsActivity::class.java)
                        .start()
            }
        }
    }


    companion object {

        fun newInstance(): MineFragment {

            val args = Bundle()

            val fragment = MineFragment()
            fragment.arguments = args
            return fragment
        }
    }
}
