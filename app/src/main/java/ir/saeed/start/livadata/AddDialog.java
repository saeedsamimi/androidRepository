package ir.saeed.start.livadata;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AddDialog extends BottomSheetDialog {
    private OnConfirmButtonClickListener OnConfirmClickListener;
    private TextInputEditText mItemNameInput,mUnitPriceInput,mCountInput;
    private BufferValues mBufferValues;
    public AddDialog(@NonNull Context context) {
        super(context);
    }

    public AddDialog(@NonNull Context context,String bufferName,long bufferUnitPrice,long bufferCount){
        super(context);
        mBufferValues = new BufferValues(bufferName,bufferUnitPrice,bufferCount);
    }

    private void setBufferItems(){
        if (mBufferValues != null) {
            mItemNameInput.setText(String.valueOf(mBufferValues.mBufferName));
            mUnitPriceInput.setText(String.valueOf(mBufferValues.mBufferUnitPrice));
            mCountInput.setText(String.valueOf(mBufferValues.mBufferCount));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setWindowAnimations(R.style.AddDialogAnimation);
        setContentView(R.layout.add_dialog_layout);
        mItemNameInput = findViewById(R.id.txtInput_FirstName);
        mUnitPriceInput = findViewById(R.id.txtInput_LastName);
        mCountInput = findViewById(R.id.txtInput_Count);
        setBufferItems();
        TextWatcher TW = new TextWatcher() {
            final TextInputLayout TIL = (TextInputLayout) mCountInput.getParent().getParent();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                String S;
                if(Objects.requireNonNull(mCountInput.getText()).length() != 0 && Objects.requireNonNull(mUnitPriceInput.getText()).length() != 0){
                    S = "Total: "+ Long.parseLong(mCountInput.getText().toString()) * Long.parseLong(mUnitPriceInput.getText().toString());
                }else S = "!NULL!";
                TIL.setHelperText(S);
            }
        };
        mUnitPriceInput.addTextChangedListener(TW);
        mCountInput.addTextChangedListener(TW);
        Button cancelButton = findViewById(R.id.Cancel_btn);
        assert cancelButton != null;
        cancelButton.setOnClickListener(view -> dismiss());
        Button confirmButton = findViewById(R.id.Confirm_btn);
        assert confirmButton != null;
        confirmButton.setOnClickListener(view -> {
            CellItem t = new CellItem(Objects.requireNonNull(mItemNameInput.getText()).toString(), Long.parseLong(Objects.requireNonNull(mUnitPriceInput.getText()).toString()),Long.parseLong(Objects.requireNonNull(mCountInput.getText()).toString()));
            OnConfirmClickListener.onConfirmClick(t);
            cancel();
        });
    }

    public void setOnConfirmClickListener(OnConfirmButtonClickListener onConfirmClickListener) {
        OnConfirmClickListener = onConfirmClickListener;
    }
    private static class BufferValues{
        private final String mBufferName;
        private final long mBufferUnitPrice,mBufferCount;
        public BufferValues(String bufferName,long bufferUnitPrice,long bufferCount){
            mBufferName = bufferName;
            mBufferUnitPrice = bufferUnitPrice;
            mBufferCount = bufferCount;
        }
    }
    public interface OnConfirmButtonClickListener{ void onConfirmClick(CellItem Name);}
}
