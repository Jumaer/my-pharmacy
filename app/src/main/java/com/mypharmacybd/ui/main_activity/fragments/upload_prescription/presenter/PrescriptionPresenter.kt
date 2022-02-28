package com.mypharmacybd.ui.main_activity.fragments.upload_prescription.presenter

import com.mypharmacybd.ui.main_activity.fragments.upload_prescription.PrescriptionContact

class PrescriptionPresenter : PrescriptionContact.Presenter {

    private var _view:PrescriptionContact.View? = null
    private val view get() = _view

    override fun setView(view: PrescriptionContact.View) {
        _view = view
    }

    override fun onViewDestroyed() {
        _view = null
    }
}