import streamlit as st

# Import the content from different .py files
from infix import tab1_content
from rpn import tab2_content
from calculator import tab3_content

# Create three tabs
tab1, tab2, tab3 = st.tabs(["INFIX", "RPN", "Normal"])

# Add content to Tab 1
with tab1:
    tab1_content()

# Add content to Tab 2
with tab2:
    tab2_content()

# Add content to Tab 3
with tab3:
    tab3_content()