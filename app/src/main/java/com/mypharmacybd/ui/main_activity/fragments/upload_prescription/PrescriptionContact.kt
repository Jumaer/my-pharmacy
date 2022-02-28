package com.mypharmacybd.ui.main_activity.fragments.upload_prescription

interface PrescriptionContact {
    interface View {

    }
    interface Presenter{
        fun setView(view:View)

        fun onViewDestroyed()
    }

    interface Model{

    }


    interface OnFinishedListener{}
}