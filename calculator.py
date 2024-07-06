import streamlit as st
import datetime

# Title and input

def tab3_content():

    st.title("Normal Calculator")

    # Session state to store the stack and input
    if "stack" not in st.session_state:
        st.session_state.stack = []
    if "input" not in st.session_state:
        st.session_state.input = ""

    # Function to handle button clicks
    def button_click(label):
        # log = f"time: {datetime.now().strftime('%Y-%m-%d %H:%M:%S.%f')[:-3]}, button click: {label}"
        # with open('./rpn.log', 'a') as f:
        #     print(log)
        #     f.write(log+'\n')
        if label == '=':
            try:
                st.session_state.input = str(int(eval(st.session_state.input)))
            except Exception as e:
                st.session_state.input = "Error"
        elif label == "C":
            st.session_state.stack = []
            st.session_state.input = ""
        else:
            if label == 'x':
                st.session_state.input += '*'
            elif label == '\u2212':
                st.session_state.input += '-'
            elif label == '\uff0b':
                st.session_state.input += '+'
            else:
                st.session_state.input += label


    # Display the stack and current input
    st.text_input("Input", value=st.session_state.input, key="normal_display", disabled=True)

    # Layout for the calculator buttons
    buttons = [
        ["7", "8", "9", "/"],
        ["4", "5", "6", "x"],
        ["1", "2", "3", "\u2212"],
        ["0", "C", "=", "\uff0b"],
        ['(', ')']
    ]

    for row in buttons:
        cols = st.columns(4)
        for i, label in enumerate(row):
            cols[i].button(label, on_click=button_click, args=(label,), key=f"normal_{label}", use_container_width=True)